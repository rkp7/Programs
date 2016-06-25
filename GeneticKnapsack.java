import java.util.*;
import java.lang.*;
import java.io.*;

class GeneticKnapsack
{
	public static void main (String[] args) throws java.lang.Exception
	{
	    // your code goes here
	    int noOfIterations = 2;
	    int noOfChromosomes = 10;
	    
	    int profits[] = {42, 12, 40, 25};
	    int weights[] = {7, 3, 4, 5};
	    int weightOfKnapsack = 10;
	    
	    chromosome chromosomes[] = init_population(profits, weights, noOfChromosomes, profits.length);
	    
	    System.out.println("\nInitial population:");
	    System.out.println("\nItem 1\tItem 2\tItem 3\tItem 4\tProfit\tWeight\tFitness");
	    for(int i=0; i<noOfChromosomes; i++) {
	        chromosomes[i].print_chromosome(profits, weights, weightOfKnapsack);
	    }
	    System.out.println();
	    
	    for(int i=0; i<noOfIterations; i++) {
	        for(int j=0; j<noOfChromosomes; j+=2) {
	            chromosome c1 = chromosomes[j];
	            chromosome c2 = chromosomes[j+1];
	            
	            crossover(c1, c2, c1.size);
	            
	            c1.mutate();
	            c2.mutate();
	            
	            /* System.out.println(" change ");
	            c1.print_chromosome(profits, weights, weightOfKnapsack);
	            c2.print_chromosome(profits, weights, weightOfKnapsack);
	            System.out.println();*/
	        }
	        System.out.println("\nPopulation after Iteration " + (i+1) + " :\n");
	        
	        int max_fitness = -1000;
	        int current_fitness = -1;
	        int ind = -1;
	        System.out.println("\nItem 1\tItem 2\tItem 3\tItem 4\tProfit\tWeight\tFitness");
        	for(int j=0; j<noOfChromosomes; j++) {
        	    chromosomes[j].print_chromosome(profits, weights, weightOfKnapsack);
        	    current_fitness = chromosomes[j].fitness(profits, weights, weightOfKnapsack);
        	    
        	    if(current_fitness > max_fitness) {
        	        max_fitness = current_fitness;
        	        ind = j;
        	    }
        	}
        	System.out.println();
	        System.out.println("The fittest chromosome of the populations is:");
	        System.out.println("\nItem 1\tItem 2\tItem 3\tItem 4\tProfit\tWeight\tFitness");
	        chromosomes[ind].print_chromosome(profits, weights, weightOfKnapsack);
	        
	    }
	    
	}
	
	static chromosome[] init_population(int[] profits, int[] weights, int noOfChromosomes, int noOfElements) {
	   chromosome chromosomes[] = new chromosome[noOfChromosomes];
	   for(int i=0; i<noOfChromosomes; i++) {
	       chromosomes[i] = new chromosome(noOfElements);
	   }
	   return chromosomes;
	}
	
	static void crossover(chromosome c1, chromosome c2, int size) {
	    int n = size/2;
	    
	    for(int i=n; i<size; i++) {
	        int temp = c1.elements[i];
	        c1.elements[i] = c2.elements[i];
	        c2.elements[i] = temp;
	    }
	}
}

class chromosome 
{
    int elements[];
    int size;
    
    chromosome(int s) {
        Random R = new Random();
        size = s;
        elements = new int[s];
        for(int i=0; i<s; i++) {
            elements[i] = R.nextInt(2);

        }
    }
    
    void mutate() {
        Random R = new Random();
        int loc = R.nextInt(size);
        if(this.elements[loc] == 0) {
            this.elements[loc] = 1;
        }else {
            this.elements[loc] = 0;
        }
    }
    
    int fitness(int[] profits, int[] weights,int limit) {
        int fit = getProfit(profits);
        int wsum = getWeight(weights);
        
        // penalty
        if(wsum > limit) {
            fit -= 50*(wsum - limit);
        }
        
        return fit;
    }
    
    int getProfit(int[] profits) {
        int p = 0;
        for(int i=0; i<size; i++) {
            p += elements[i]*profits[i];
        }
        return p;
    }
    
    int getWeight(int[] weights) {
        int w = 0;
        for(int i=0; i<size; i++) {
            w += elements[i]*weights[i];
        }
        return w;
    }
    
    void print_chromosome(int[] profits, int[] weights, int limit) {
	   StringBuilder s = new StringBuilder();
	   for(int j=0; j < this.size; j++) {
	      s.append(this.elements[j] + "\t\t");
	   }
	   System.out.println(s.toString() + this.getProfit(profits) + "\t\t" + this.getWeight(weights) + "\t\t" + this.fitness(profits, weights, limit));
    }
}

/*

Output:

Initial population:

Item 1	Item 2	Item 3	Item 4	Profit	Weight	Fitness
1		1		1		0		94		14		-106
0		0		0		1		25		5		25
1		1		1		1		119		19		-331
1		0		0		0		42		7		42
0		0		0		1		25		5		25
0		0		0		1		25		5		25
0		0		1		1		65		9		65
0		0		0		0		0		0		0
1		1		1		0		94		14		-106
1		0		0		0		42		7		42


Population after Iteration 1 :


Item 1	Item 2	Item 3	Item 4	Profit	Weight	Fitness
1		1		1		1		119		19		-331
1		0		1		0		82		11		32
1		0		0		0		42		7		42
1		0		1		0		82		11		32
0		1		0		1		37		8		37
0		0		1		1		65		9		65
0		0		1		0		40		4		40
1		0		1		1		107		16		-193
1		0		0		0		42		7		42
1		0		1		1		107		16		-193

The fittest chromosome of the populations is:

Item 1	Item 2	Item 3	Item 4	Profit	Weight	Fitness
0		0		1		1		65		9		65

Population after Iteration 2 :


Item 1	Item 2	Item 3	Item 4	Profit	Weight	Fitness
1		1		1		1		119		19		-331
1		0		0		1		67		12		-33
1		0		0		0		42		7		42
1		0		0		1		67		12		-33
0		0		1		1		65		9		65
0		0		0		0		0		0		0
1		0		1		1		107		16		-193
1		0		0		0		42		7		42
1		0		1		0		82		11		32
1		1		0		0		54		10		54

The fittest chromosome of the populations is:

Item 1	Item 2	Item 3	Item 4	Profit	Weight	Fitness
0		0		1		1		65		9		65

*/