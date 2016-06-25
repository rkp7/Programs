import java.sql.*;
import java.util.*;
import java.text.DecimalFormat;
 
public class NaiveBayesClassification {
    public static void main(String[] args) {
 
        // creates Connection object
        Connection conn1 = null;
 
        try {
            // connect to database
            String url1 = "jdbc:mysql://localhost:3306/bayes";
            String user = "root";
            String password = "";
 
	    // check if connection has been established
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database Bayes\n");

		Scanner sc = new Scanner(System.in);

		// Create object df for to format the double obtained in calculations
		DecimalFormat df = new DecimalFormat(".###");
	
		System.out.println("Enter new record:");
		// Input new record from command line
		// Format: <Age> <Income> <Student> <Credits>
		String newRecord = sc.nextLine();

		// Split the input string for separating each field of input
		String newRecordFields[] = newRecord.split(" ");
		
		// Create stmt object for SQL statement
		Statement stmt = conn1.createStatement();
		
		// Execute SQL query for obtaining all rows of table where customer buys a computer
		ResultSet rs1 = stmt.executeQuery("SELECT * FROM tab WHERE class = 'Yes' ");
		
		// Initialize counters to record number of rows
		int buysComputerYes = 0;
		int ageNewRecordBuysComputerYes = 0;
		int incomeNewRecordBuysComputerYes = 0;
		int creditNewRecordBuysComputerYes = 0;
		int studentNewRecordBuysComputerYes = 0;

		// Loop through rows of rs1
		while(rs1.next()) {
			// Store the current row values in temporary variables
			String age = rs1.getString("Age");
			String income = rs1.getString("Income");
			String student = rs1.getString("Student");
			String credit = rs1.getString("Credits");
			
			// Increment the counters according while matching the new record fields
			buysComputerYes++;
			if(age.equals(newRecordFields[0])) 
				ageNewRecordBuysComputerYes++;
			if(income.equals(newRecordFields[1])) 
				incomeNewRecordBuysComputerYes++;
			if(student.equals(newRecordFields[2])) 
				studentNewRecordBuysComputerYes++;
			if(credit.equals(newRecordFields[3])) 
				creditNewRecordBuysComputerYes++;
				
			
		}
		
		// Execute SQL query for obtaining all rows of table where customer doesn't buy a computer
		ResultSet rs2 = stmt.executeQuery("SELECT * FROM tab WHERE Class = 'No' ");

		// Initialize counters to record number of rows
		int buysComputerNo = 0;
		int ageNewRecordBuysComputerNo = 0;
		int incomeNewRecordBuysComputerNo = 0;
		int creditNewRecordBuysComputerNo = 0;
		int studentNewRecordBuysComputerNo = 0;

		// Loop through rows of rs2
		while(rs2.next()) {
			// Store the current row values in temporary variables
			String age = rs2.getString("Age");
			String income = rs2.getString("Income");
			String student = rs2.getString("Student");
			String credit = rs2.getString("Credits");
			
			// Increment the counters according while matching the new record fields
			buysComputerNo++;
			if(age.equals(newRecordFields[0])) 
				ageNewRecordBuysComputerNo++;
			if(income.equals(newRecordFields[1])) 
				incomeNewRecordBuysComputerNo++;
			if(student.equals(newRecordFields[2])) 
				studentNewRecordBuysComputerNo++;
			if(credit.equals(newRecordFields[3])) 
				creditNewRecordBuysComputerNo++;
				
			
		}

		// Calculate the total rows as addition of number of rows of the two mutually exclusive queries
		int totalRecords = buysComputerNo + buysComputerYes;

		// Calculate probability whether a customer buys a computer or not in the given data
		double PbuysComputerYes = (double)buysComputerYes/totalRecords;
		double PbuysComputerNo = (double)buysComputerNo/totalRecords;
		
		// Display the above probabilities
		System.out.println();
		System.out.println("P(Buys): " + df.format(PbuysComputerYes) + "\t\t\tP(Doesn't buy): " + df.format(PbuysComputerNo));
		System.out.println();

		// Calculate conditional probabilty for the new record
		double PNewRecordBuysComputerYes = (double)ageNewRecordBuysComputerYes/buysComputerYes *
						   (double)incomeNewRecordBuysComputerYes/buysComputerYes *
						   (double)studentNewRecordBuysComputerYes/buysComputerYes *
						   (double)creditNewRecordBuysComputerYes/buysComputerYes;
		
		double PNewRecordBuysComputerNo = (double)ageNewRecordBuysComputerNo/buysComputerNo *
						  (double)incomeNewRecordBuysComputerNo/buysComputerNo *
						  (double)studentNewRecordBuysComputerNo/buysComputerNo *
						  (double)creditNewRecordBuysComputerNo/buysComputerNo;
		
		// Display the above calculated probability
		System.out.println("P(X/Buys): " +df.format(PNewRecordBuysComputerYes) + "\t\t\tP(X/Doesn't buy): " + df.format(PNewRecordBuysComputerNo));
		System.out.println();
		
		// Finally calculate the overall probability whether the new customer record will buy a computer or not
		double totalPNewRecordBuysComputerYes = PbuysComputerYes*PNewRecordBuysComputerYes;
	
		double totalPNewRecordBuysComputerNo = PbuysComputerNo*PNewRecordBuysComputerNo;

		// Display the final output of classification
		System.out.println("P(X/Buys)*P(Buys): " +df.format(totalPNewRecordBuysComputerYes) + "\t\tP(X/Doesn't buy)*P(Doesn't buy): " + df.format(totalPNewRecordBuysComputerNo));
		System.out.println();
							
		if(totalPNewRecordBuysComputerYes > totalPNewRecordBuysComputerNo) {
			System.out.println("Prediction: Yes \nAs per Bayesian Classification, the new customer will buy a computer.");
		} 
		else {
			System.out.println("Prediction: No\nAs per Bayesian Classification, the new customer won't buy a computer.");
		}
		
            }
 
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
    }
}