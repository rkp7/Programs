#include<stdio.h>
#include<time.h>
#define MAX 100000
void quicksort(int a[],int l,int h);
int partition(int a[],int l,int h);
int main()
{
	int n,i,j;
	int a[MAX];
	time_t start,end;
	/*printf("Worst case time complexity:\n\n");
	for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            a[i]=i;
        }
        quicksort(a,0,n-1);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }*/
    printf("\n\nAverage and best case time complexity:\n\n");
    for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            a[i]=rand();
        }
        quicksort(a,0,n-1);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }
    return(0);
}
int partition(int a[],int l,int h)
{
	int i,j,p,temp;
	i = l;
	j = h;
	p = a[l];
	while(i<j)
	{
		while(a[i]<=p && i<j)
		{
			i++;
		}
		while(a[j]>p)
		{
			j--;
		}
		if(i<j)
		{
			temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
	}
	a[l] = a[j];
	a[j] = p;
	return(j);
}



void quicksort(int a[],int l,int h)
{
	int pivot;
	if(l<h)
	{
		pivot = partition(a,l,h);
		quicksort(a,l,pivot-1);
		quicksort(a,pivot+1,h);
	}
}
