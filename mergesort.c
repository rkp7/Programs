#include<stdio.h>
#include<time.h>
#define MAX 100000
void merge(int a[],int l, int mid, int h);
void mergesort(int a[],int l,int h);
void main()
{
	int n,i,j;//%=
	int a[MAX];
	time_t start,end;
	/*start = time(NULL);
	printf("Enter the no. of elements:\n");
	scanf("%d",&n);*/
	printf("Worst case time complexity:\n\n");
	for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            j=n-i;
            a[i]=j;
        }
        mergesort(a,0,n-1);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }
    printf("\n\nBest case time complexity:\n\n");
    for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            a[i]=i;
        }
        mergesort(a,0,n-1);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }
    printf("\n\nAverage case time complexity:\n\n");
    for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            a[i]=rand();
        }
        mergesort(a,0,n-1);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }
	/*end = time(NULL);
	printf("time = %lf",difftime(end,start));*/

}
void merge(int a[],int l, int mid, int h)
{
	int i = l;
	int j = mid+1;
	int k = 0;
        int c[MAX];
	while(i<=mid && j<=h)
	{
		if(a[i]<a[j])
		{
			c[k] = a[i];
			k++; i++;
		}
		else
		{
			c[k] = a[j];
			k++; j++;
		}
	}
	while(i<= mid)
	{
		c[k] = a[i];
		k++; i++;
	}
	while(j<= h)
	{
		c[k] = a[j];
		k++; j++;
	}
	for(i=l,k=0; i<= h; i++,k++)
	{
		a[i] = c[k];
	}
}


void mergesort(int a[],int l,int h)
{
	int mid = (l + h)/2;
	if(h>l)
	{
		mergesort(a,l,mid);
		mergesort(a,mid + 1,h);
		merge(a,l,mid,h);
	}
}
