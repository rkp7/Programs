//A2 - 1211026

#include<stdio.h>
#define MAX 10

char ele[MAX][MAX];
int w[MAX][MAX],c[MAX][MAX],r[MAX][MAX],p[MAX],q[MAX];
void prefix_traversal(int i,int j);

void main()
{

	int temp=0,root,min,min1,n;
	int i,j,k,b;

	printf("Enter the number of elements:");
	scanf("%d",&n);
	printf("\n");
	for(i=0; i<n; i++)
	{
		printf("Enter element %d: ",i+1);
 		scanf("%s",ele[i]);
	}
	printf("\n");
	for(i=1; i<=n; i++)
	{
		printf("Enter the success probability of %d:",i);
		scanf("%d",&p[i]);
	}
	printf("\n");
	for(i=0; i<=n; i++)
	{
		printf("Enter the failure probability of %d:",i);
		scanf("%d",&q[i]);
	}
	printf("W\t\tC\t\tR\n");
	for(i=0; i<=n; i++)
	{
		for(j=0; j<=n; j++)
		{
			if(i == j)
			{
				w[i][j] = q[i];
			 	c[i][j] = 0;
				r[i][j] = 0;
				printf("\nW[%d][%d]: %d\tC[%d][%d]: %d\tR[%d][%d]: %d\n",i,j,w[i][j],i,j,c[i][j],i,j,r[i][j]);
			}
		}
	}
	printf("\n");
	for(b=0; b<n; b++)
	{
		for(i=0,j=b+1; j<n+1 && i<n+1; j++,i++)
		{
			if(i!=j && i<j)
			{
				w[i][j] = p[j] + q[j] + w[i][j-1];

				min = 30000;
				for(k = i+1; k<=j; k++)
				{
					min1 = c[i][k-1] + c[k][j] + w[i][j];
					if(min > min1)
					{
						min = min1;
						temp = k;
					}
				}
				c[i][j] = min;
				r[i][j] = temp;
			}
			printf("W[%d][%d]: %d\tC[%d][%d]: %d\tR[%d][%d]: %d\n",i,j,w[i][j],i,j,c[i][j],i,j,r[i][j]);
		}
		printf("\n");
	}

    printf("The tree in parenthesized prefix is as follows:\n");
    for (i=0;i<=n;i++)
      for (j=0;j<=n-i;j++)
      {
        printf("c(%d,%d) with cost %d Tree: ",j,j+i,c[j][j+i]);
        prefix_traversal(j,j+i);
        printf("\n");
      }

    printf("\nMinimum cost = %d\n",c[0][n]);

}

void prefix_traversal(int i,int j)
// prints optimal binary search tree
{
    if (i<j)
    {
      printf("%s",ele[r[i][j]-1]);
      if (i<r[i][j]-1 && r[i][j]<j)
      {
        printf("(");
        prefix_traversal(i,r[i][j]-1);
        printf(",");
        prefix_traversal(r[i][j],j);
        printf(")");
      }
      else if (i<r[i][j]-1)
      {
        printf("(");
        prefix_traversal(i,r[i][j]-1);
        printf(",");
        printf(")");
      }
      else if (r[i][j]<j)
      {
        printf("(");
        printf(",");
        prefix_traversal(r[i][j],j);
        printf(")");
      }
    }
}

/*

Output:

Enter the number of elements:4

Enter element 1: do
Enter element 2: if
Enter element 3: int
Enter element 4: while

Enter the success probability of 1:3
Enter the success probability of 2:3
Enter the success probability of 3:1
Enter the success probability of 4:1

Enter the failure probability of 0:2
Enter the failure probability of 1:3
Enter the failure probability of 2:1
Enter the failure probability of 3:1
Enter the failure probability of 4:1
W               C               R

W[0][0]: 2      C[0][0]: 0      R[0][0]: 0

W[1][1]: 3      C[1][1]: 0      R[1][1]: 0

W[2][2]: 1      C[2][2]: 0      R[2][2]: 0

W[3][3]: 1      C[3][3]: 0      R[3][3]: 0

W[4][4]: 1      C[4][4]: 0      R[4][4]: 0

W[0][1]: 8      C[0][1]: 8      R[0][1]: 1
W[1][2]: 7      C[1][2]: 7      R[1][2]: 2
W[2][3]: 3      C[2][3]: 3      R[2][3]: 3
W[3][4]: 3      C[3][4]: 3      R[3][4]: 4

W[0][2]: 12     C[0][2]: 19     R[0][2]: 1
W[1][3]: 9      C[1][3]: 12     R[1][3]: 2
W[2][4]: 5      C[2][4]: 8      R[2][4]: 3

W[0][3]: 14     C[0][3]: 25     R[0][3]: 2
W[1][4]: 11     C[1][4]: 19     R[1][4]: 2

W[0][4]: 16     C[0][4]: 32     R[0][4]: 2

The tree in parenthesized prefix is as follows:
c(0,0) with cost 0 Tree:
c(1,1) with cost 0 Tree:
c(2,2) with cost 0 Tree:
c(3,3) with cost 0 Tree:
c(4,4) with cost 0 Tree:
c(0,1) with cost 8 Tree: do
c(1,2) with cost 7 Tree: if
c(2,3) with cost 3 Tree: int
c(3,4) with cost 3 Tree: while
c(0,2) with cost 19 Tree: do(,if)
c(1,3) with cost 12 Tree: if(,int)
c(2,4) with cost 8 Tree: int(,while)
c(0,3) with cost 25 Tree: if(do,int)
c(1,4) with cost 19 Tree: if(,int(,while))
c(0,4) with cost 32 Tree: if(do,int(,while))

Minimum cost = 32

*/









