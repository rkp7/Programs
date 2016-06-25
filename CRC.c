/*
    NAME: RAJ PALKAR
    ROLL NO : 1211026
    This program generates message with CRC code and
    helps in error detection
*/

#include <stdio.h>
#define MAX 100

int M[MAX];
int G[MAX];
int Cbits,Mbits,Gbits,Tbits;

void getMessage();
int check(int temp[],int f);
int detectError();

int main(void)
{
    int i,choice;

    do
    {
        printf("\n1-Code generation\n2-Error detection\n3-Exit\nEnter choice:");
        scanf("%d",&choice);

        switch(choice)
        {
            case 1 :    printf("Enter the total no. of bits in message:");
                        scanf("%d",&Mbits);

                        printf("Enter the no. of CRC bits:");
                        scanf("%d",&Cbits);

                        printf("Enter the message bits (one by one):\n");
                        for(i=0; i<Mbits; i++)
                        {
                            scanf("%d",&M[i]);
                        }

                        while(i < Mbits + Cbits)
                        {
                            M[i] = 0;
                            i++;
                        }

                        printf("Enter the total no. of bits in generator:");
                        scanf("%d",&Gbits);

                        printf("Enter the generator bits (one by one):\n");
                        for(i=0; i<Gbits; i++)
                        {
                            scanf("%d",&G[i]);
                        }

                        getMessage();

                        printf("The message is :\n");
                        for(i=0; i < Mbits + Cbits; i++)
                        {
                            printf("%d ",M[i]);
                        }

	                    break;

	        case 2 :    printf("Enter the no. of bits of recieved code:");
	                    scanf("%d",&Tbits);

	                    printf("Enter the recieved code (one bit at a time from the LSB):\n");
                        for(i=0; i< Tbits; i++)
                        {
                            scanf("%d",&M[i]);
                        }

                        printf("Enter the total no. of bits in generator:");
                        scanf("%d",&Gbits);

                        printf("Enter the generator bits (one by one):\n");
                        for(i=0; i<Gbits; i++)
                        {
                            scanf("%d",&G[i]);
                        }

	                    i = detectError();

	                    if( i == 0 )
	                        printf("There is no error\n");
	                    else
	                        printf("Recieved code has error\n");

                        break;

            case 3 :    break;

            default:    printf("Invalid choice\n");
        }

    }while(choice!=3);



    return 0;
}

// checks whether the temporary message has become zero
int check(int temp[],int f)
{
    int i,c;

    if(f == 0)
        c = Mbits;
    else
        c = Tbits;

    for(i=0; i<c; i++)
    {
        if(temp[i]!=0)
            return 1;
    }

    return 0;
}

// function to generate entire code with CRC bits
void getMessage()
{
    int temp[MAX];
    int i,j;

    // copy in another array
    for(i=0; i < Mbits + Cbits; i++)
    {
        temp[i] = M[i];
    }

    // traverse each bit from MSB one bit at a time
    for(i=0; i < Mbits + Cbits - Gbits + 1 && check(temp,0); i++)
    {
        // if the first bit is zero no need to process that
        if( temp[i] == 0)
            continue;

	   // perform exor with the bits of generator
        for(j=0; j<Gbits; j++)
        {
            if(temp[i+j] == G[j])
            {
                temp[i+j] = 0;
            }
            else
            {
                temp[i+j] = 1;
            }
        }
    }

    // copy the remainder generated
    for(i=Mbits; i < Mbits + Cbits; i++)
    {
        M[i] = temp[i];
    }


}

// checks whether the recieved code contains error
int detectError()
{
    int i,j,temp[MAX];

    // copy the message bits in another array
    for(i=0; i < Tbits; i++)
    {
        temp[i] = M[i];
    }

    // traverse bits from MSB one bit at a time
    for(i=0; i<Tbits-Gbits+1 && check(temp,1); i++)
    {
	   // no need to process if the first bit is zero
        if( temp[i] == 0)
            continue;

	   // perform exor with generator bits
        for(j=0; j<Gbits; j++)
        {
            if(temp[i+j] == G[j])
            {
                temp[i+j] = 0;
            }
            else
            {
                temp[i+j] = 1;
            }
        }
    }

    // if the remainder is not zero then there is error
    for(i=0; i<Tbits; i++)
    {
        if(temp[i] != 0)
            return 1;
    }

    // in case no error present
    return 0;
}

/*

Output:


1-Code generation
2-Error detection
3-Exit
Enter choice:1
Enter the total no. of bits in message:10
Enter the no. of CRC bits:4
Enter the message bits (one by one):
1 1 0 1 0 1 1 0 1 1
Enter the total no. of bits in generator:5
Enter the generator bits (one by one):
1 0 0 1 1
The message is :
1 1 0 1 0 1 1 0 1 1 1 1 1 0
1-Code generation
2-Error detection
3-Exit
Enter choice:2
Enter the no. of bits of recieved code:14
Enter the recieved code (one bit at a time from the LSB):
1 1 0 1 0 1 1 0 1 1 0 1 1 0
Enter the total no. of bits in generator:5
Enter the generator bits (one by one):
1 0 0 1 1
Recieved code has error

1-Code generation
2-Error detection
3-Exit
Enter choice:3

*/
