//A2-1211026

#include<stdio.h>
#include<string.h>
#define MAX 20

void main()
{
	int i=0,j=0,xn,yn,c;
	char X[MAX],Y[MAX];
	int mat[MAX][MAX],temp;
	char check[MAX][MAX];
	char str[MAX];

	printf("Enter the X sequence:");
	scanf("%s",X);
	xn = strlen(X);
	printf("Enter the Y sequence:");
	scanf("%s",Y);
	yn = strlen(Y);
	printf("\n\nThe required table is as follows:\n\n");
	printf("\t%c\t",'Y');
	for(i = 0;i <= yn; i++)
	{
		printf("%c\t",Y[i]);
	}
	printf("\n");
	for(i = 0;i <= xn; i++)
	{
		if(i==0)
			printf("%c\t",'X');
		else
			printf("%c\t",X[i-1]);
		for(j = 0;j <= yn; j++)
		{
			if(i == 0 || j == 0)
			{
				mat[i][j] = 0;
				printf("%d\t",mat[i][j]);
				check[i][j] = '0';
			}
			else if(X[i-1] == Y[j-1])
			{
				mat[i][j] = mat[i-1][j-1] + 1;
				printf("%d diag\t",mat[i][j]);
				check[i][j] = 'd';
			}
			else if(i > 0 && j > 0 )
				{
					if(mat[i-1][j] >= mat[i][j-1])
					{
						mat[i][j] = mat[i-1][j];
						printf("%d up\t",mat[i][j]);
						check[i][j] = 'u';
					}
					else
					{
						mat[i][j] = mat[i][j-1];
						printf("%d left\t",mat[i][j]);
						check[i][j] = 'l';
					}
				}
		}
		printf("\n");
	}
	c = 0;
	j = yn;
	for(i = xn; i>=0 ; i--)
	{
		while(j>0 && i>0)
		{
			if(check[i][j] == 'd')
			{
				str[c++] = X[i-1];
				j--;
				break;
			}
			else if(check[i][j] == 'u')
			{
				break;
			}
			else if(check[i][j] ==  'l')
			{
				j--;
			}
		}
	}
	str[c] = '\0';
	strrev(str);
	printf("\nThe LCS is %s",str);
	printf("\nLength is %d",strlen(str));
}

/*

Output:

Enter the X sequence:AGGTAB
Enter the Y sequence:GMTANB


The required table is as follows:

        Y       G       M       T       A       N       B
X       0       0       0       0       0       0       0
A       0       0 up    0 up    0 up    1 diag  1 left  1 left
G       0       1 diag  1 left  1 left  1 up    1 up    1 up
G       0       1 diag  1 up    1 up    1 up    1 up    1 up
T       0       1 up    1 up    2 diag  2 left  2 left  2 left
A       0       1 up    1 up    2 up    3 diag  3 left  3 left
B       0       1 up    1 up    2 up    3 up    3 up    4 diag

The LCS is GTAB
Length is 4

*/


