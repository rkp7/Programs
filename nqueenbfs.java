import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

class nqueenbfs
{
    static int n;
    static int column_no[];
    
	public static void main (String[] args) throws java.lang.Exception
	{
        // Initialize the value of n
		n = 4;
		column_no = new int[n+1];
		Tree T = new Tree(null, new int[n+1]);
		nqueens(1,T,1);

        System.out.println("Following is the bfs result for " + n + "-queens problem:\n");
        bfs(T);
	}
	
	// function for breadth first search
	public static void bfs(Tree T) {
	    
	    Queue q = new Queue();
	    q.enqueue(T);
	    System.out.print("Level 0 Live state: ( ");
	    for(int j=1; j<=n; j++) {
	       if(j!=n)         
	           System.out.print(T.value[j] + ", ");
	       else
	            System.out.print(T.value[j]);
	                    
    	}
    	System.out.println(")");
	   
	    while(!q.isempty()) {
	        Tree temp = q.dequeue();
	        //System.out.println("Dequeued " + temp.value[0] + temp.value[1] + temp.value[2]+ temp.value[3]+ temp.value[4] );
	        Iterator itr = temp.children.iterator();
	        while(itr.hasNext()) {
	            Tree temp1 = (Tree)itr.next();
	            q.enqueue(temp1);
	            //System.out.println("Enqueued " + temp1.value[0] + temp1.value[1] + temp1.value[2]+ temp1.value[3]+ temp1.value[4] );
	            
	            if(temp1.value[0] < 0) {
	                System.out.print("Level " + (-temp1.value[0]) + " Dead state: ( ");
	            }
	            else if(temp1.value[0] == 0) {
	                System.out.print("Reached Goal state: ( ");
	            }
	            else {
	                System.out.print("Level " + temp1.value[0] + " Live state: ( ");
	            }
	            
	            for(int j=1; j<=n; j++) {
	                if(j!=n)         
	                    System.out.print(temp1.value[j] + ", ");
	                else
	                    System.out.print(temp1.value[j]);
	                    
	            }
	            System.out.println(")");
	        }
	    }
	}
	
	// function for generating state space tree of nqueens problem
	public static void nqueens(int row_no, Tree parent, int level)
    {
        int i,c;
        int state[] = new int[n+1];
        
        for(c=1; c<=n; c++)
        {
            for(int j=0; j<n+1; j++) {
                state[j] = parent.value[j];
            }
            
            state[row_no] = c;
            state[0] = -level;
            
            Tree temp = new Tree(parent, state);
            parent.addChildren(temp);
            
            /*System.out.print(c + " added to parent with value " + parent.value + " ");
            for(int k=0; k<=n; k++) {
                System.out.print(" " + parent.value[k] + " ");
            }
            System.out.println();
            */
            
            if(check(row_no,c))
            {
                column_no[row_no] = c;
                temp.value[0] = level;
                if(row_no == n)
                {
                    temp.value[0] = 0;
                    /*for(i=1; i<=n; i++)
                    {
    		            System.out.print("\nQueen " + i + " placed at - row: " + i + " column: " + column_no[i]);
                        //System.out.println("goal added to parent with value " + temp.value);
                        //temp.addChildren(new Tree(temp, -2));
                    }
                    System.out.println("goal added to parent with value " + temp.value);
                    temp.addChildren(new Tree(temp, new int[]{-2,-2,-2,-2,-2}));
    		        System.exit(0);
                    */
                }
                else
                {   
                    nqueens(row_no + 1, temp, level+1);
                }
            }
        }
    }

    // function for checking whether queens attack each other
	public static boolean check(int row_no,int c)
    {
        int i;
    
        for(i=1; i<=row_no-1; i++)
        {
            if((column_no[i] == c)||(Math.abs(column_no[i] - c) == Math.abs(i - row_no)))
            {
                return(false);
            }
        }
        return(true);
    }
}

// class for state space tree
class Tree
{
    int value[];
    Tree parent;
    ArrayList<Tree> children = new ArrayList();
    
    Tree(Tree p, int v[]) {
        value = new int[v.length];
        for(int i=0; i<v.length; i++) {
            value[i] = v[i];
        }
        parent = p;
    }
    
    public void addChildren(Tree c) {
        children.add(c);
    }
    
    public Tree getChild(int i) {
        return children.get(i);
    }
    
    public boolean noChild() {
        if(children.size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}

// Class for queue data structure
class Queue {
    ArrayList<Tree> nodes = new ArrayList();
    
    public void enqueue(Tree t) {
        nodes.add(t);
    }
    
    public Tree dequeue() {
        Tree temp = nodes.get(0);
        nodes.remove(0);
        return temp;
    }
    
    public boolean isempty() {
        if(nodes.size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
}