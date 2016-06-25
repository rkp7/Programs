/*
    NAME: RAJ PALKAR
    ROLL NO : 1211026
    This program is used to generate checksum
    which is then concatenated at end of the message
*/

#include <stdio.h>
#include <string.h>
#define MAX 100

char msg[MAX];
int num[MAX];
int countn=0;
long sum=0;
char final[MAX];
int countf = 0;

void convert_dec(char m[],int t);
void break_msg();
long power(int a,int b);
void addcarry();
void convert_hex(long sum);
void reverse();
void complement();

int main(void)
{

    int i,j,n;

    printf("Enter the message:\n");
    scanf("%s",msg);

    n = strlen(msg);

    break_msg();

    for(i=0; i<countn; i++)
        sum = sum + num[i];

    convert_hex(sum);

    addcarry();

    convert_hex(num[countn-1]);

    printf("\n");

    reverse();

    complement();

    printf("\nThe checksum is: ");

    for(i=0; i<4; i++)
        printf("%c",final[i]);

    printf("\nThe entire transmitted string is: ");
    for(i=0; i<n; i++)
        printf("%c",msg[i]);
    for(i=0; i<4; i++)
        printf("%c",final[i]);

    printf("\nEnter the recieved message:\n");
    scanf("%s",msg);

    n = strlen(msg);

    countn = 0;
    break_msg();
    sum = 0;

    for(i=0; i<countn; i++)
        sum = sum + num[i];

    convert_hex(sum);

    addcarry();

    convert_hex(num[countn-1]);

    printf("\n");

    reverse();

    complement();

    for(i=0; i<4; i++)
    {
        if(final[i] != '0')
            break;
    }

    if(i==4)
        printf("\nNo Errors");
    else
        printf("\nError present");

    return 0;
}

void complement()
{
    int i;
    char c;

    for(i=0; i<4; i++)
    {
        switch(final[i])
        {
            case '0': c = 'F';
                        break;
            case '1': c = 'E';
                        break;
            case '2': c = 'D';
                        break;
            case '3': c = 'C';
                        break;
            case '4': c = 'B';
                        break;
            case '5': c = 'A';
                        break;
            case '6': c = '9';
                        break;
            case '7': c = '8';
                        break;
            case '8': c = '7';
                        break;
            case '9': c = '6';
                        break;
            case 'A': c = '5';
                        break;
            case 'B': c = '4';
                        break;
            case 'C': c = '3';
                        break;
            case 'D': c = '2';
                        break;
            case 'E': c = '1';
                        break;
            case 'F': c = '0';
                        break;
      }

      final[i] = c;
  }
}

void reverse()
{
    int i,c;

    for(i=0; i<2; i++)
    {
        c = final[i];
        final[i] = final[3-i];
        final[3-i] = c;
    }
}

void addcarry()
{
    int c;

    reverse();

    convert_dec(final,0);

    switch(final[4])
    {
            case '0': c = 0;
                        break;
            case '1': c = 1;
                        break;
            case '2': c = 2;
                        break;
            case '3': c = 3;
                        break;
            case '4': c = 4;
                        break;
            case '5': c = 5;
                        break;
            case '6': c = 6;
                        break;
            case '7': c = 7;
                        break;
            case '8': c = 8;
                        break;
            case '9': c = 9;
                        break;
            case 'A': c = 10;
                        break;
            case 'B': c = 11;
                        break;
            case 'C': c = 12;
                        break;
            case 'D': c = 13;
                        break;
            case 'E': c = 14;
                        break;
            case 'F': c = 15;
                        break;

   }

   num[countn-1] = num[countn-1] + c;

}
void convert_hex(long sum)
{
    int i;
    char c;
    long temp = sum;

    countf = 0;

    while(temp>0)
    {
        i = temp%16;

        switch(i)
        {
            case 0: c = '0';
                        break;
            case 1: c = '1';
                        break;
            case 2: c = '2';
                        break;
            case 3: c = '3';
                        break;
            case 4: c = '4';
                        break;
            case 5: c = '5';
                        break;
            case 6: c = '6';
                        break;
            case 7: c = '7';
                        break;
            case 8: c = '8';
                        break;
            case 9: c = '9';
                        break;
            case 10: c = 'A';
                        break;
            case 11: c = 'B';
                        break;
            case 12: c = 'C';
                        break;
            case 13: c = 'D';
                        break;
            case 14: c = 'E';
                        break;
            case 15: c = 'F';
                        break;
        }

        final[countf++] = c;

        temp = temp/16;

   }
}




long power(int a,int b)
{
    int i;
    long prod=1;

    for(i=1; i<=b; i++)
    {
        prod=prod*a;
    }

    return prod;
}

void break_msg()
{
    int i,n;

    n = strlen(msg);

    for(i=0; i<n; i=i+4)
    {
        convert_dec(msg,i);
    }

}

void convert_dec(char m[],int t)
{
    int i,c;
    long temp = 0;
    int j=1;

    for(i=t; i<t+4; i++)
    {
        switch(m[i])
        {
            case '0': c = 0;
                        break;
            case '1': c = 1;
                        break;
            case '2': c = 2;
                        break;
            case '3': c = 3;
                        break;
            case '4': c = 4;
                        break;
            case '5': c = 5;
                        break;
            case '6': c = 6;
                        break;
            case '7': c = 7;
                        break;
            case '8': c = 8;
                        break;
            case '9': c = 9;
                        break;
            case 'A': c = 10;
                        break;
            case 'B': c = 11;
                        break;
            case 'C': c = 12;
                        break;
            case 'D': c = 13;
                        break;
            case 'E': c = 14;
                        break;
            case 'F': c = 15;
                        break;
        }

        temp = temp + c*power(16,4-j);
        j++;
   }

   num[countn++] = temp;
}

/*

Ouput:

Enter the message:
0100F203F4F5F6F7


The checksum is: 210E
The entire transmitted string is: 0100F203F4F5F6F7210E
Enter the recieved message:
0100F203F4F5F6F7210E


No Errors

*/
