/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class KMeansClustering
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter the number of elements: ");
		int noOfElements = sc.nextInt();
		
		ArrayList<Integer> elementsArrayList = new ArrayList();
		
		for(int i = 0; i < noOfElements; i++) {
			System.out.print("Enter element " + (i+1) + " : ");
			elementsArrayList.add(sc.nextInt());  
		}
		
		System.out.print("Enter the number of clusters: ");
		int noOfClusters = sc.nextInt();

		System.out.print("Enter the maximum number of iterations: ");
		int noOfIterations = sc.nextInt();
		
		performClustering(elementsArrayList, noOfClusters, noOfIterations);
	}
	
	static void performClustering(ArrayList<Integer> elements, int noOfClusters, int noOfIterations) {
	    
	    Cluster[] clusters = initializeClusters(elements, noOfClusters);
	    Cluster[] previousClusters = new Cluster[noOfClusters];
	    //System.out.println(" a" + clusters[0].compareClusters(clusters[1]));

	    int iteration = 1;
	    do 
	    {
	        double meanValues[] = new double[noOfClusters];
	        
	        for(int i=0; i<noOfClusters; i++) {
	        	// generate mean of cluster i
	            meanValues[i] = clusters[i].calculateClusterMean();
	            // store a copy of previous cluster
	            previousClusters[i] = clusters[i].copyCluster();
	            // create new cluster
	            clusters[i] = new Cluster();
	            
	            //System.out.println(previousClusters[i]);
	            //System.out.println(meanValues[i]);
	        }
	        
	        // associate each element to the new clusters based on new mean values
	        for(Integer element: elements) {
    	        int index = getClusterIndex(meanValues, element, noOfClusters);
    	        clusters[index].add(element);
	        }
	        
	        System.out.println("\nAfter Iteration " + iteration + " :");
	        for(int i=0; i<noOfClusters; i++) {
	            System.out.println("Mean: " + meanValues[i] + "  Cluster: " + clusters[i]);
	        }

	        iteration++;
	    
	    // loop until the clusters no longer differ or maximum iterations are reached 
	    } while(clustersNotEqual(clusters, previousClusters, noOfClusters) && iteration < noOfIterations);
	    
	}
	
	// initialize the clusters
	static Cluster[] initializeClusters(ArrayList<Integer> elements, int noOfClusters) {
	    
	    Cluster clusters[] = new Cluster[noOfClusters];
	    for(int i=0; i<noOfClusters; i++) {
	        clusters[i] = new Cluster();
	    }
	    
	    Set<Integer> randomIndices = generateRandomIndices(noOfClusters, elements.size());
	    
	    /*Set<Integer> randomIndices = new HashSet<Integer>();
	    randomIndices.add(1);
	    randomIndices.add(4);*/
	    
	    double initialMeanValues[] = new double[noOfClusters];
	    
	    int counter = 0;
	    for(Integer randomIndex: randomIndices) {
	        //System.out.println(elements.get((int)randomIndex));
	        initialMeanValues[counter++] = (double)elements.get((int)randomIndex);
	    }
	    
	    for(Integer element: elements) {
	        int index = getClusterIndex(initialMeanValues, element, noOfClusters);
	        clusters[index].add(element);
	    }
	    
	    System.out.println("\nInitialization ");
	    for(int i=0; i<noOfClusters; i++) {
	        System.out.println("Mean: " + initialMeanValues[i] + "  Cluster: " + clusters[i]);
	    }
	    
	    return clusters;
	}
	
	// generate random indices for initializing mean values prior to first iteration
	static Set<Integer> generateRandomIndices(int noOfClusters, int noOfElements) {
	    
	    Random r = new Random();
	    
	    Set<Integer> set = new HashSet<Integer>();
        while(set.size() < noOfClusters) {
            set.add(r.nextInt(noOfElements));
        }
	    
	    //System.out.println(set);
	    return set;
	}
	
	// find the cluster to which the element belongs based on minimum difference from cluster mean 
	static int getClusterIndex(double meanValues[], int element, int noOfClusters) {
	    
	    double minDifference = Math.abs(element - meanValues[0]);
	    int minIndex = 0;
	    
	    for(int i=1; i<noOfClusters; i++) {
	        if(Math.abs(element - meanValues[i]) < minDifference) {
	            minDifference = Math.abs(element - meanValues[i]);
	            minIndex = i;
	        }
	    }
	    
	    return minIndex;
	}
	
	// check whether all the current clusters are completely equal to the clusters in the previous iteration
	static boolean clustersNotEqual(Cluster[] currentClusters, Cluster[] previousClusters, int noOfClusters) {
        
        for(int i=0; i<noOfClusters; i++) {
            if(!currentClusters[i].compareClusters(previousClusters[i]))
                return true;
        }
        return false;
	}
}

class Cluster {
    
    ArrayList<Integer> elements;
    
    // override default constructor
    Cluster() {
        elements = new ArrayList<Integer>();    
    }
    
    // calculate the mean of the cluster
    double calculateClusterMean() {
	    
	    int noOfElements = elements.size();
	    Iterator itr = elements.iterator();
	    
	    int sum = 0;
	    while(itr.hasNext()) {
	        sum += (int)itr.next();
	    }
	    
	    if(noOfElements != 0)
	        return (double)sum/noOfElements;
	    else
	        return 0;
	}
    
    // add element to cluster
    void add(Integer element) {
        elements.add(element);
    }
    
    // override the public toString method for displaying clusters
    public String toString() {
        Iterator itr = elements.iterator();
        StringBuilder sb = new StringBuilder();
        while(itr.hasNext()) {
            sb.append(itr.next() + " | ");
        }
        return sb.toString();
    }
    
    // check whether the two clusters are equal or not
    boolean compareClusters(Cluster cluster) {
        if(this.elements.size() != cluster.elements.size())
            return false;
        
        Collections.sort(this.elements);
        Collections.sort(cluster.elements);
        
        return this.elements.equals(cluster.elements);
    }
    
    // create a copy of the cluster with same elements
    Cluster copyCluster() {
        Cluster c = new Cluster();
        
        Iterator itr = elements.iterator();
        while(itr.hasNext()) {
            c.add((Integer)itr.next());
        }
        
        return c;
    }
    
    
}

/*

Sample Output:

Enter the number of elements: 9
Enter element 1 : 2
Enter element 2 : 4
Enter element 3 : 10
Enter element 4 : 12
Enter element 5 : 3
Enter element 6 : 20
Enter element 7 : 30
Enter element 8 : 11
Enter element 9 : 25
Enter the number of clusters: 3
Enter the maximum number of iterations: 10

Initialization
Mean: 2.0  Cluster: 2 | 3 |
Mean: 4.0  Cluster: 4 | 10 | 12 | 11 |
Mean: 25.0  Cluster: 20 | 30 | 25 |

After Iteration 1 :
Mean: 2.5  Cluster: 2 | 4 | 3 |
Mean: 9.25  Cluster: 10 | 12 | 11 |
Mean: 25.0  Cluster: 20 | 30 | 25 |

After Iteration 2 :
Mean: 3.0  Cluster: 2 | 4 | 3 |
Mean: 11.0  Cluster: 10 | 12 | 11 |
Mean: 25.0  Cluster: 20 | 30 | 25 |

*/