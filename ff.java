/*

Raj Palkar - 1211026
Generalized first
Java program to find the first set of any given grammar
*/

import java.util.*;

public class ff{
	
	public static void main(String args[]){

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter no. of terminals: ");
		int noOfTerminals = sc.nextInt();
		
		String terminals[] = new String[noOfTerminals];
		System.out.println("Enter the terminal symbols:");
		
		for(int i =0; i<noOfTerminals; i++)
		{
			terminals[i] = sc.next();
		}

		System.out.print("Enter no. of non-terminals: ");
		int noOfNonTerminals = sc.nextInt();

		String nonTerminals[] = new String[noOfNonTerminals];
		String grammar[][]  = new String[noOfNonTerminals][20];
		int grammarLength[] = new int[noOfNonTerminals];
		
		for(int i =0; i<noOfNonTerminals; i++)
		{
			System.out.println("Enter the non-terminal symbol " + (i+1) + " :");
			nonTerminals[i] = sc.next();
			System.out.println("Enter the transitions for non-terminal " + nonTerminals[i] + " seperated by a slash (Enter ! for epsilon):");
			System.out.print(nonTerminals[i] + " -> ");
			String transition = sc.next();
			grammar[i] = transition.split("/");
		}
		
		System.out.println("\nGiven grammar is: ");
		for(int i =0; i<noOfNonTerminals; i++)
		{
			System.out.print(nonTerminals[i] + " -> ");
			for(int j=0; j<grammar[i].length; j++)
			{
				System.out.print(grammar[i][j] + " ");
				if(j!=grammar[i].length -1)
					System.out.print("/");
			}
			System.out.println();
		}

		first(noOfTerminals, terminals, noOfNonTerminals, nonTerminals, grammar);
	}

	static void first(int noOfTerminals, String terminals[], int noOfNonTerminals, String nonTerminals[], String grammar[][])	
	{

		boolean flag[][] = new boolean[noOfNonTerminals][20];
		char firstResult[][] = new char[noOfTerminals + noOfNonTerminals][20];
		int count[] = new int[noOfNonTerminals];
		Arrays.fill(count,0);
		boolean kflag = false;

		for(int i=0; i<noOfTerminals; i++)
		{
			firstResult[i][0] = terminals[i].charAt(0);
		}
		
		int i = 0;
		int j;
		int nonTerminalsCovered = 0;
		while(nonTerminalsCovered <= noOfNonTerminals)
		{
			kflag = false;
			for(j=0; j<grammar[i].length; j++)
			{
				if(flag[i][j] == true)
					continue;

				String singleTransition = grammar[i][j];
				//System.out.println(singleTransition);
				
				if(singleTransition.equals("!"))
				{
					firstResult[i+noOfTerminals][count[i]] = '!';
					//System.out.println("epsillon for " + nonTerminals[i] + " " + singleTransition + " " + firstResult[i+noOfTerminals][count[i]] + " .");
					count[i] = count[i] + 1;
					flag[i][j] = true;
				}
				else
				{
					int k;
					for(k=0; k<noOfTerminals; k++)
					{
						if(singleTransition.indexOf(terminals[k]) == 0)
							break;
					}

					if(k != noOfTerminals)
					{
						firstResult[i+noOfTerminals][count[i]] = singleTransition.charAt(0);
						flag[i][j] = true;
						//System.out.print("terminal for " + nonTerminals[i] + " " + singleTransition + " " + firstResult[i+noOfTerminals][count[i]] + " .");
						count[i] = count[i] + 1;
					}
					else
					{	
						for(k=0; k<noOfNonTerminals; k++)
						{
							//System.out.println(singleTransition + " " + nonTerminals[k] + " " + k);
							if(singleTransition.indexOf(nonTerminals[k]) == 0)
								break;
						}
						
						if(count[k] == 0)
						{
							i = k;
							kflag = true;
						}
						else
						{
							for(int m =0; m<count[k]; m++)
							{
								firstResult[i+noOfTerminals][count[i]] = firstResult[noOfTerminals+k][m];
								//System.out.println(firstResult[i+noOfTerminals][count[i]] + " " + firstResult[noOfTerminals+k][m] + " " + k + " " +  i);
								count[i] = count[i] + 1;	
							}

						}
						//System.out.println(" else " + i);
					}
			
				}		
		
			}

			if(j == grammar[i].length && kflag == false)
			{
				i = (i + 1)%noOfNonTerminals;
				nonTerminalsCovered++;
			}
		}

		for(i=0; i<noOfNonTerminals; i++)
		{
			System.out.println();
			System.out.print("First of " + nonTerminals[i] + " = { ");

			for(j=0; j<count[i]; j++)
			{
				System.out.print(firstResult[i+noOfTerminals][j] + " "); 
				if(j!=count[i] - 1)
					System.out.print(",");
			}

			System.out.println(" }");
		}		
	}
}

/*

Ouptut:

C:\Users\Raj\Documents>javac ff.java

C:\Users\Raj\Documents>java ff
Enter no. of terminals: 5
Enter the terminal symbols:
+
*
(
)
i
Enter no. of non-terminals: 5
Enter the non-terminal symbol 1 :
E
Enter the transitions for non-terminal E seperated by a slash (Enter ! for epsil
on):
E -> TA
Enter the non-terminal symbol 2 :
A
Enter the transitions for non-terminal A seperated by a slash (Enter ! for epsil
on):
A -> +TA/!
Enter the non-terminal symbol 3 :
T
Enter the transitions for non-terminal T seperated by a slash (Enter ! for epsil
on):
T -> FB
Enter the non-terminal symbol 4 :
B
Enter the transitions for non-terminal B seperated by a slash (Enter ! for epsil
on):
B -> *FB/!
Enter the non-terminal symbol 5 :
F
Enter the transitions for non-terminal F seperated by a slash (Enter ! for epsil
on):
F -> (E)/i

Given grammar is:
E -> TA
A -> +TA /!
T -> FB
B -> *FB /!
F -> (E) /i

First of E = { i ,(  }

First of A = { + ,!  }

First of T = { i ,(  }

First of B = { * ,!  }

First of F = { i ,(  }

*/