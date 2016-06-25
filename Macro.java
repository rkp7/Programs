import java.util.*;
import java.io.*;

/*

Owner: Raj K. Palkar
E-mail: rajpalkar26@gmail.com

Program for simulating the operation of two pass macro processor with macro calls using positional arguments.
In pass 1, macros are identified and their definition is stored.
In pass 2, macro calls are recoginzed in the program and we expand the macro calls to produce the required output.

*/

public class Macro {

	// Macro Definition Table
	private static List<String> MDT = new ArrayList();
	// Macro Definition Table Counter indicates next index at which new macro definition must be stored
	private static int mdtc = 0;
	// Macro Name Table - Macro name associated with MDT start index for corresponding definition
	private static Map<String,Integer> MNT = new HashMap();
	// Macro Name Table Counter - indicates next index at which new entry must be stored
	private static int mntc = 0;
	// Indicates total number of arguments
	private static int argNo = 0;
	// Argument List Array
	private static List<String> ALA = new ArrayList();

	// Reader and Writer Objects for pass 1 and pass 2 of Macro Processor
	static BufferedReader inputPass1File,inputPass2File;
	static FileWriter mdtFile,mntFile,outputPass1File,outputPass2File;

	public static void main(String args[]) throws Exception {
		
		// Current directory path
		String dir = "C:\\Users\\Raj\\Documents\\SPCC\\macro\\";

		// Get the required files for pass 1
		inputPass1File = new BufferedReader(new FileReader(dir + "input.txt"));
		mdtFile = new FileWriter(new File(dir + "mdt.txt"));
		mntFile = new FileWriter(new File(dir + "mnt.txt"));
		outputPass1File = new FileWriter(new File(dir + "output_pass1.txt"));

		// Call for pass 1 of two pass macro processor
		pass1();

		// Close all the files which were required for pass 1
		outputPass1File.flush();
		outputPass1File.close();
		mdtFile.flush();
		mdtFile.close();
		mntFile.flush();
		mntFile.close();
		inputPass1File.close();

		// Open the required files for pass 2
		inputPass2File = new BufferedReader(new FileReader(dir + "output_pass1.txt"));
		outputPass2File = new FileWriter(new File(dir + "output_pass2.txt"));

		// Call for pass 2 of two pass macro processor
		pass2();

		// Close all the files which were required for pass 2
		inputPass2File.close();
		outputPass2File.flush();
		outputPass2File.close();
	}

	/*
	Function for pass 1 of Macro Processor
	It involves identification of Macros and Storing Macro Definition
	*/

	static void pass1() throws Exception {

		String line;
		// Loop till we reach the end of file
		while((line = inputPass1File.readLine()) != null) {
			// Check for MACRO pseudo-op indicating start of Macro in the program
			if(line.equalsIgnoreCase("MACRO")) {
				// We process the Macro Definition now
				processMacroDefinition();
			}
			else {
				outputPass1File.write(line + "\n");
			}
		}
	}

	/*
	Function for storing macro definition in MDT and corresponding entry in MNT
	Also, here the formal arguments are replaced with positional indicators from ALA
	*/

	static void processMacroDefinition() throws Exception {

		String line = inputPass1File.readLine();
		String args[] = line.split("\\s+");
		/*
		We now have macro prototype of form 
		arg[0] <- Macro Name
		arg[1..n] <- Arguments
		*/

		// Place the macro name in MNT along with the current mdt index for macro
		MNT.put(args[0], mdtc);
		mntFile.write(mntc + "\t\t" + args[0] + "\t\t" + mdtc + "\n");
		mntc += 1;

		// Enter the macro prototype in MDT as it is
		MDT.add(mdtc + "\t\t" + line + "\n");
		mdtFile.write(mdtc + "\t\t" + line + "\n");
		mdtc += 1;

		// Now only have the arguments in the args vector
		args = args[1].split(",");

		// For each argument we make an entry in ALA table
		for(String a:args) {
			ALA.add(a);
			argNo += 1;
		}

		// Read the next lines till the MEND pseudo-op is encountered
		while((line = inputPass1File.readLine()).equalsIgnoreCase("MEND") == false) {

			// Temporarily store the line
			String modifiedLine = line;

			// Loop through each argument in the current macro
			for(String a:args) {
				// Find the index if the argument is present in current line
				int ind = modifiedLine.indexOf(a);
				if(ind >= 0) {
					// Replace the argument with positional indicators
					modifiedLine = modifiedLine.substring(0,ind) + " #" + ALA.indexOf(a) + " " + modifiedLine.substring(ind+a.length());  
				}
			}

			// Write the macro definition line in MDT along with line no
			MDT.add(mdtc + "\t\t" + modifiedLine + "\n");
			mdtFile.write(mdtc + "\t\t" + modifiedLine + "\n");
			mdtc += 1;
		}

		// We need MEND pseudo-op at end of macro to indicate the end of Macro
		MDT.add(mdtc + "\t\t" + line + "\n");
		mdtFile.write(mdtc + "\t\t" + line + "\n");
		mdtc += 1;
	}

	/*
	Function for pass 2 of Macro processor
	It involves identification of Macro Calls and Expansion of Macros
	*/

	static void pass2() throws Exception{
		// Temporary variable to store each line being read
		String line;

		// Loop through each line of the pass 1 output of two pass macro processor
		while((line = inputPass2File.readLine()) != null) {

			// Remove any unwanted spaces and split the current line 
			String call[] = line.trim().split("\\s+");
			// Flag to indicate whether we found a Macro Call
			Boolean flag = false;

			// Traverse through all macro names in MNT and check whether the first op in the form of call[0] has a macro call to any macros listed in MNT
			for(String k:MNT.keySet()) {

				if(call[0].equalsIgnoreCase(k)) {
					// Call the function to expand the Macro
					processMacroCall(call, k);
					flag = true;
					break;
				}
			}

			// If the statement didn't include a Macro Call
			if(flag == false) {
				outputPass2File.write(line + "\n");
			}
		}
	}

	/*
	Function for expanding Macro call in pass 2
	call <- tokens of Macro call statement read from output of pass 1
	macroName <- name of Macro 
	*/

	static void processMacroCall(String[] call, String macroName) throws Exception{

		// Get MDT index from MNT table
		int index = MNT.get(macroName);
		// Get the prototype for current Macro from MDT at index obtained in previous step
		String prototype = MDT.get(index);

		// Split the prototype to get individual formal arguments
		String args[] = prototype.split("\\s+");
		args = args[2].trim().split("[,\\s+]");

		// We require the positional indicators for each argument as in ALA formed in pass 1
		int position[] = new int[args.length];
		int i = 0;
		// For each argument store the corresponding positional indicator in position array
		for(String a:args) {
			position[i] = ALA.indexOf(a);
			i += 1;
		}

		// Split the Macro call to get the actual arguments. We consider here positional arguments
		String callArgs[] = call[1].split(",");

		i = 0;
		// For each of the positional indicator in ALA replace formal arguments with actual arguments
		for(String a:callArgs) {
			ALA.set(position[i],a);
			i += 1;
		}

		// Lookup the macro definition from MDT till we read MEND pseudo-op
		for(int j = index+1; (MDT.get(j)).indexOf("MEND") < 0; j++) {
			// Store the current MDT entry
			String mdtEntry = MDT.get(j);
			String modifiedEntry = mdtEntry;

			// Check whether the MDT statement contains any of the arguments in the form of positional indicators
			for(int k=0; k<args.length; k++) {

				// Get the index of positional indicator corresponding to current argument
				int ind = mdtEntry.indexOf("#" + position[k]);

				// If there exists a positional indicator in current statement, replace it with the corresponding formal argument from ALA
				if(ind >= 0) {
					String actualArgName = ALA.get(position[k]);
					modifiedEntry = " " + mdtEntry.substring(1,ind) +  actualArgName + mdtEntry.substring(ind+2);
				}
			}

			// Write the expanded Macro Call version to the output of Pass 2
			outputPass2File.write(modifiedEntry);
		}
	}
}

/* 

Sample Input and Output: 

input.txt - input to pass 1

MACRO
RKP1 &NUM1,&NUM2
A	1,&NUM1
L	2,&NUM2
MEND
MACRO
INCR &ARG1,&ARG2,&ARG3
A	1,&ARG1
A	2,&ARG2
A	3,&ARG3
MEND
PRG2		START
        	USING		*,BASE
		INCR		DATA1,DATA2,DATA3
		RKP1		DATA1,DATA2
DATA1		DC		F'5'
DATA2 		DC		F'10'
DATA3		DC		F'15'
BASE    	EQU		8
TEMP    	DS		1F
DROP		8
        	END


MDT 

0		RKP1 &NUM1,&NUM2
1		A	1, #0 
2		L	2, #1 
3		MEND
4		INCR &ARG1,&ARG2,&ARG3
5		A	1, #2 
6		A	2, #3 
7		A	3, #4 
8		MEND

MNT

0		RKP1		0
1		INCR		4

Output of pass 1 <-> Input to pass 2

PRG2		START
        	USING		*,BASE
		INCR		DATA1,DATA2,DATA3
		RKP1		DATA1,DATA2
DATA1		DC		F'5'
DATA2 		DC		F'10'
DATA3		DC		F'15'
BASE    	EQU		8
TEMP    	DS		1F
DROP		8
        	END

Output of pass 2

PRG2		START
        	USING		*,BASE
 		A	1, DATA1 
 		A	2, DATA2 
 		A	3, DATA3 
 		A	1, DATA1 
 		L	2, DATA2 
DATA1		DC		F'5'
DATA2 		DC		F'10'
DATA3		DC		F'15'
BASE    	EQU		8
TEMP    	DS		1F
DROP		8
        	END

*/

