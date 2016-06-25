/*

Raj K. Palkar
rajpalkar26@gmail.com
Program for simulating Apriori Algorithm

*/

import java.util.*;
import java.io.*;
import java.sql.*;

public class RKPApriori {
	
	// value for minimum occurance of value in transactions
	private static int boundaryValue = 2;

	public static void main(String args[]) {
		
		// create object for connecting to sql database
		Connection conn1 = null;
		
		try {
				String url = "jdbc:mysql://localhost:3306/apriori";
				String user = "root";
				String password = "";

				// Assign sql driver to the connection object
				conn1 = DriverManager.getConnection(url, user, password);
            		if(conn1 != null) {
                		System.out.println("Connected to the database Apriori\n");
			
				// Execute query for getting total count of rows
				Statement stmt = conn1.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM " + args[0]);
				int noOfTransactions = 0;
				while(rs.next()) {
					noOfTransactions = rs.getInt("count");
				}
				

				Transaction transactions[] = new Transaction[noOfTransactions];
				
				// Fetch all data from transactions presented in the input
				rs = stmt.executeQuery("SELECT * FROM " + args[0]);
				int counter = 0;
				System.out.println("Data:\nTID\tItems");
				while(rs.next()) {
					transactions[counter] = new Transaction();

					// fetch, store and display id of transaction
					int TID = rs.getInt("TID");
					transactions[counter].TID = TID;
					System.out.print(TID + "\t");

					// fetch, store and display the items of transaction
					String items = rs.getString("Items");
					String itemsArray[] = items.split(" ");
					
					for(String s: itemsArray) {
						transactions[counter].items.add(Integer.parseInt(s));
						System.out.print(s + " ");
					}
					System.out.println();
					
					counter++;
				}
				System.out.println();

				// Calculate count of each individual value in transactions
				HashMap<ArrayList<Integer>, Integer> countMap = new HashMap<>();
				for(Transaction t: transactions) {

					for(Integer item: t.items) {

						ArrayList<Integer> itemList = new ArrayList<>();
						itemList.add(item);

						if(countMap.containsKey(itemList)){
							countMap.put(itemList, countMap.get(itemList) + 1);
							//System.out.println("added item: " + itemList);
						}
						else {
							countMap.put(itemList, 1);
							//System.out.println("incremented: " + item);
						}

					}

				}
				
				// display the count of individual values
				displayCurrentStage(countMap, "C1");
	
				// Initialize C to count of individual values in items
				HashMap<ArrayList<Integer>, Integer> C = countMap;
				HashMap<ArrayList<Integer>, Integer> prevC;

				int s = 2;
				do {
					// Store the previous values in C vector
					prevC = C;

					// Replicate values of C in L
					HashMap<ArrayList<Integer>, Integer> L = C;

					// Create an arraylist to store values which need to be removed due to boundary condition
					ArrayList rL = new ArrayList();
					// Create a hashset 
					Set<Integer> set = new HashSet<Integer>();

					// loop through all elements of L
					for(Map.Entry m:L.entrySet()){
						// if the boundary condition fails, we add it to removal list
						if((Integer)m.getValue() < boundaryValue) {
							rL.add(m.getKey());
							//countMap.remove(m.getKey());
						}
						// otherwise we create a set holding the values which satisfy the boundary condition
						else {
							ArrayList<Integer> tempList = (ArrayList)m.getKey(); 
							Iterator temp = tempList.iterator();

							while(temp.hasNext()) {
								set.add((Integer)temp.next());
							}

						}
						//System.out.println(m.getKey()+" "+m.getValue());
					}

					// Remove those elements from L which have count less than boundary value as stored in removal list
					Iterator iterator = rL.iterator();
					while(iterator.hasNext()) {
						L.remove(iterator.next());
					}
					
					// Display the itemsets in L
					if(L.size() != 0)
						displayCurrentStage(L, "L" + Integer.toString(s-1));
				
					// Allocate a new object tof Hashmap to C
					C = new HashMap<>();

					// Loop through all itemsets in L
					for(Map.Entry m:L.entrySet()){

						// Create arraylist based on key of the map
						ArrayList<Integer> tempList = (ArrayList)m.getKey();
			
						// iterate through values of set which passed the boundary condition
						iterator = set.iterator();
						while(iterator.hasNext()){

							// Create a temporary hashset
							HashSet<Integer> tempSet = new HashSet(tempList);
							// Add the current value of set to the hashset
							int a = (Integer)iterator.next();
							tempSet.add(a);
							// if the size is equal to current iteration, proceed to inner clause
							if(tempSet.size() == s) {
								// Iterate through the tempset and add it to an arraylist
								ArrayList<Integer> tempList1 = new ArrayList<>();
								Iterator iterator1 = tempSet.iterator();
								while(iterator1.hasNext()){
									tempList1.add((Integer)iterator1.next());
								}
								// store the arraylist and respective count as itemsets in C
								C.put(tempList1, countFromData(tempList1, transactions));

							}

						}

					}
					
					// Display the itemsets in C
					if(C.size() != 0)
						displayCurrentStage(C, "C" + Integer.toString(s));
					s++;

				// Continue until no further itemsets can be associated
				}while(C.size() > 1);

				// display final output
				displayCurrentStage(prevC, "final association itemsets");
				
			}		
		} catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
	}

	// function to display the contents of hashmap and stage of consideration
	static void displayCurrentStage(HashMap<ArrayList<Integer>, Integer> M, String s) {
		System.out.println("Elements in " + s + " :");
		for(Map.Entry m:M.entrySet()){  
			System.out.println("Itemset : " + m.getKey() +"  Count : " + m.getValue());  
		}
		System.out.println();
	}
	 
	// functon to count the number of occurances of a specific arraylist in transactions
	static int countFromData(ArrayList<Integer> list1, Transaction T[]) {
		int count = 0;
		for(Transaction t:T) {
			if((t.items).containsAll(list1)) {
				count += 1;
			}
		}
		return count;	 	
	 }

}

// class for transaction
class Transaction {
	int TID;
	ArrayList<Integer> items = new ArrayList<Integer>();
}

/*

Output for data 1:

C:\Users\Raj\Downloads>javac RKPApriori.java
Note: RKPApriori.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.

C:\Users\Raj\Downloads>java -cp mysql-connector-java-5.1.38-bin.jar.; RKPApriori
 transactions
Connected to the database Apriori

Data:
TID     Items
100     1 3 4
200     2 3 5
300     1 2 3 5
400     2 5

Elements in C1 :
Itemset : [1]  Count : 2
Itemset : [2]  Count : 3
Itemset : [3]  Count : 3
Itemset : [4]  Count : 1
Itemset : [5]  Count : 3

Elements in L1 :
Itemset : [1]  Count : 2
Itemset : [2]  Count : 3
Itemset : [3]  Count : 3
Itemset : [5]  Count : 3

Elements in C2 :
Itemset : [1, 2]  Count : 1
Itemset : [2, 3]  Count : 2
Itemset : [1, 3]  Count : 2
Itemset : [3, 5]  Count : 2
Itemset : [2, 5]  Count : 3
Itemset : [1, 5]  Count : 1

Elements in L2 :
Itemset : [2, 3]  Count : 2
Itemset : [1, 3]  Count : 2
Itemset : [3, 5]  Count : 2
Itemset : [2, 5]  Count : 3

Elements in C3 :
Itemset : [1, 2, 3]  Count : 1
Itemset : [1, 3, 5]  Count : 1
Itemset : [2, 3, 5]  Count : 2
Itemset : [1, 2, 5]  Count : 1

Elements in L3 :
Itemset : [2, 3, 5]  Count : 2

Elements in final association itemsets :
Itemset : [2, 3, 5]  Count : 2




Output for data 2:

C:\Users\Raj\Downloads>java -cp mysql-connector-java-5.1.38-bin.jar.; RKPApriori
 transactions1
Connected to the database Apriori

Data:
TID     Items
100     1 2 5
101     2 4
102     2 3
103     1 2 4
104     1 3
105     2 3
106     1 3
107     1 2 3 5
108     1 2 3

Elements in C1 :
Itemset : [1]  Count : 6
Itemset : [2]  Count : 7
Itemset : [3]  Count : 6
Itemset : [4]  Count : 2
Itemset : [5]  Count : 2

Elements in L1 :
Itemset : [1]  Count : 6
Itemset : [2]  Count : 7
Itemset : [3]  Count : 6
Itemset : [4]  Count : 2
Itemset : [5]  Count : 2

Elements in C2 :
Itemset : [1, 2]  Count : 4
Itemset : [2, 3]  Count : 4
Itemset : [3, 4]  Count : 0
Itemset : [4, 5]  Count : 0
Itemset : [1, 3]  Count : 4
Itemset : [2, 4]  Count : 2
Itemset : [3, 5]  Count : 1
Itemset : [1, 4]  Count : 1
Itemset : [2, 5]  Count : 2
Itemset : [1, 5]  Count : 2

Elements in L2 :
Itemset : [1, 2]  Count : 4
Itemset : [2, 3]  Count : 4
Itemset : [1, 3]  Count : 4
Itemset : [2, 4]  Count : 2
Itemset : [2, 5]  Count : 2
Itemset : [1, 5]  Count : 2

Elements in C3 :
Itemset : [1, 2, 3]  Count : 2
Itemset : [1, 3, 4]  Count : 0
Itemset : [1, 4, 5]  Count : 0
Itemset : [1, 2, 4]  Count : 1
Itemset : [2, 3, 4]  Count : 0
Itemset : [1, 3, 5]  Count : 1
Itemset : [2, 4, 5]  Count : 0
Itemset : [1, 2, 5]  Count : 2
Itemset : [2, 3, 5]  Count : 1

Elements in L3 :
Itemset : [1, 2, 3]  Count : 2
Itemset : [1, 2, 5]  Count : 2

Elements in C4 :
Itemset : [1, 2, 3, 5]  Count : 1

Elements in final association itemsets :
Itemset : [1, 2, 3]  Count : 2
Itemset : [1, 2, 5]  Count : 2

*/
