/*
    NAME: RAJ PALKAR
    ROLL NO : 1211026
    This program generates hamming code and
    helps in error detection
*/

#include <stdio.h>

#define MAX 100

int hammingcode[MAX];
int tbits,pbits,mbits;
int recCode[MAX];
int temporary_storage[MAX];
int counter = 1;

int noOfBits();
int power(int a,int b);
void fillHammingCode();
void checkParity(int pos,int flag);
void printHamming();
int detectError();

int main(void)
{
	int i,choice;

    do
    {
        printf("\n1-Hamming code generation\n2-Error detection\n3-Exit\nEnter choice:");
        scanf("%d",&choice);

        switch(choice)
        {
            case 1 :    printf("Enter the no. of message bits:");
	                    scanf("%d",&mbits);

	                    pbits = noOfBits();
	                    tbits = pbits + mbits;

	                    printf("\nTotal bits of hamming code = %d\n",tbits);

                        for(i=1; i<= tbits; i++)
		                    hammingcode[i] = 0;

	                    fillHammingCode();
	                    printf("\n");

                        printf("The transmitted hamming code is (from the LSB):\n");
	                    printHamming();

	                    break;

	        case 2 :    printf("Enter the total no. of bits:\n");
	                    scanf("%d",&tbits);

	                    printf("Enter the recieved code (one bit at a time from the LSB):\n");
                        for(i=1; i<= tbits; i++)
                        {
                            scanf("%d",&recCode[i]);
                        }

	                    i = detectError();
	                    printf("\nThe error occured at %d position\n",i);

                        break;

            case 3 :    break;

            default:    printf("Invalid choice\n");
        }

    }while(choice!=3);

    return(0);

}

// function for calculation of no of parity bits
int noOfBits()
{
	int i;

	for(i=0; power(2,i) < mbits + i + 1; i++);

	return(i);
}

// function for finding value of a raised to b (a^b)
int power(int a,int b)
{
	int i,product = 1;

	for(i=0; i<b; i++)
	{
		product = product*a;
	}
	return(product);
}

// function for generating the hamming code
void fillHammingCode()
{
	int i,c1=0;

	printf("\nEnter the message(one bit at a time starting from the LSB):\n");

	// placing the message bits in proper positions
	for(i=1; i<=tbits; )
	{
		if( i != power(2,c1))
		{
			scanf("%d",&hammingcode[i]);
		}
		else
		{
			c1++;
		}
		i++;
	}

	// obtaining values for parity bits
	c1 = 0;
	for(i=1; i<=tbits; )
	{
		if( i == power(2,c1))
		{
			checkParity(i,0);
			c1++;
		}
		i++;

	}

}

// function to check parity
// when flag is 0, used for hamming code generation otherwise for error detection
void checkParity(int pos,int flag)
{
	int i,c=pos-1,j=1,xoropt;
	int temp[MAX];

    // locating the values of only those positions checked by parity at pos
	for(i = pos; i <= tbits;)
	{
		if(c)
		{
		    i++;
		    if(flag == 0)
                temp[j] = hammingcode[i];
            else
                temp[j] = recCode[i];
			j++;
			c--;
		}
		else
		{
			i = i + pos;
			c = pos;
		}
	}

    // performing xor operation to get the parity bits
	xoropt = temp[1];
	for(i=2; i<j; i++)
	{
		if( xoropt == temp[i])
			xoropt = 0;
		else
			xoropt = 1;
	}

    // depending on value of flag, used for generation or error detection
	if(flag == 0)
        hammingcode[pos] = xoropt;
    else
        temporary_storage[counter++] = xoropt;
}

// displaying the hamming code
void printHamming()
{
	int i;

	for(i=1; i<=tbits; i++)
		printf("%d ",hammingcode[i]);

    printf("\n");
}

// detecting the error in reccieved code
int detectError()
{
    int i,c = 0,t,pos = 0;
    int temp[MAX],error[MAX];

    // Here we store only the values of parity bits from recieved code
    // and compute the actual parity bit value
    for(i=1; i<=tbits; i++)
    {
        if( i == power(2,c))
		{
            temp[c+1] = recCode[i];
            checkParity(i,1);
            c++;
		}
    }

    // compare actual and recieved parity bit values
    for(i=1; i<=c; i++)
    {
        if(temp[i] == temporary_storage[i])
            error[i] = 0;
        else
            error[i] = 1;
    }

    // Reverse the order of stored values for ease
    for(i=1; i<=c/2; i++)
    {
        t = error[i];
        error[i] = error[c-i+1];
        error[c-i+1] = t;
    }

    // calculate the decimal equivalent of binary value of error position
    for(i=1; i<=c; i++)
    {
        pos = pos + error[i]*power(2,c-i);
    }

    return pos;
}

/*

Output:


1-Hamming code generation
2-Error detection
3-Exit
Enter choice:1
Enter the no. of message bits:7

Total bits of hamming code = 11

Enter the message(one bit at a time starting from the LSB):
1 0 0 1 0 1 0

The transmitted hamming code is (from the LSB):
0 1 1 1 0 0 1 1 0 1 0

1-Hamming code generation
2-Error detection
3-Exit
Enter choice:2
Enter the total no. of bits:
11
Enter the recieved code (one bit at a time from the LSB):
0 1 1 1 0 1 1 1 0 1 0

The error occured at 6 position

1-Hamming code generation
2-Error detection
3-Exit
Enter choice:3

*/

