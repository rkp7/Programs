/*
Roll no - 1211026
Recursive descent parser for grammar
E -> (T) | a
T -> bT | c

*/

import java.util.*;

public class recursiveDescent{
	static char parseChars[];
	static int next = 0;
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);

		System.out.println("Consider the given grammar:");	
		System.out.println("E -> (T)| a");
		System.out.println("T -> bT | c");
		System.out.print("Enter the string which you want to parse:");
		String parseString = sc.next();
		parseChars = parseString.toCharArray();
		for(int i=0; i<parseChars.length; i++)
			System.out.print(parseChars[i]);
		System.out.println();
		boolean result = E();
		if(result == false)
			System.out.println("Invalid string: Cannot be parsed");
		else
			System.out.println("String is successfully parsed");
	}

	static boolean E(){
		int save = next;
			if(parseChars[next] == 'a' && next == parseChars.length -1)
			{
				next++;
				return true;
			}
			else
			{
				if(parseChars[next++] != '(')
				{
					next = save;
					return false;
				}
				if(T() == false){
					next = save;
					return false;	
				}
				if(next < parseChars.length)
				{
					if(parseChars[next++] != ')')
					{
						next = save;
						return false;
					}
				}
				else
				{
					next = save;
					return false;
				}
				return true;
			}
	}

	static boolean T(){
		int save = next;
			boolean result1;
			if(parseChars[next] == 'b')
			{
				next++;
				if(T() == false)
				{
					next = save;
					return false;
				}	
				return true;
			}
			else if(parseChars[next++] == 'c' || next > parseChars.length)
			{	
				return true;
			}
			next = save;
			return false;
			
	}
}

/*

Output:

C:\Users\Raj\Documents>javac recursiveDescent.java

C:\Users\Raj\Documents>java recursiveDescent
Consider the given grammar:
E -> (T)| a
T -> bT | c
Enter the string which you want to parse:a
a
String is successfully parsed

C:\Users\Raj\Documents>java recursiveDescent
Consider the given grammar:
E -> (T)| a
T -> bT | c
Enter the string which you want to parse:abc
abc
Invalid string: Cannot be parsed

C:\Users\Raj\Documents>java recursiveDescent
Consider the given grammar:
E -> (T)| a
T -> bT | c
Enter the string which you want to parse:(bbbc)
(bbbc)
String is successfully parsed

C:\Users\Raj\Documents>java recursiveDescent
Consider the given grammar:
E -> (T)| a
T -> bT | c
Enter the string which you want to parse:(c)
(c)
String is successfully parsed

C:\Users\Raj\Documents>java recursiveDescent
Consider the given grammar:
E -> (T)| a
T -> bT | c
Enter the string which you want to parse:(ab
(ab
Invalid string: Cannot be parsed

C:\Users\Raj\Documents>java recursiveDescent
Consider the given grammar:
E -> (T)| a
T -> bT | c
Enter the string which you want to parse:c)
c)
Invalid string: Cannot be parsed

*/