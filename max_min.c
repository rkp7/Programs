//A2-1211026
#include<stdio.h>
#include<math.h>
#include<time.h>
#define MAX 100000
void maxmin(int a[],int n,int p[]);
void MaxMin(int a[],int lb,int ub,int p[]);

int main()
{
	int a[MAX];
	int i,j,n,key,max,min;
	int p[2];
	time_t start,end;
	printf("Worst case time complexity for recursive algorithm:\n\n");
	for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            j=n-i;
            a[i]=j;
        }
        MaxMin(a,0,n,p);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }
    printf("\n\nBest case time complexity for recursive algorithm:\n\n");
    for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            a[i]=i;
        }
        MaxMin(a,0,n,p);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }
    printf("\n\nAverage case time complexity for recursive algorithm:\n\n");
    for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            a[i]=rand();
        }
        MaxMin(a,0,n,p);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }
    printf("Worst case time complexity for iterative algorithm:\n\n");
	for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            j=n-i;
            a[i]=j;
        }
        maxmin(a,n-1,p);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }
    printf("\n\nBest case time complexity for iterative algorithm:\n\n");
    for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            a[i]=i;
        }
        maxmin(a,n-1,p);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }
    printf("\n\nAverage case time complexity for iterative algorithm:\n\n");
    for(n=10000;n<=100000;n=n+5000)
    {
        start = time(NULL);
        for(i=0;i<n;i++)
        {
            a[i]=rand();
        }
        maxmin(a,n-1,p);
        end = time(NULL);
        printf("time for sorting %d nos= %lf\n",n,difftime(end,start));
    }
	//MaxMin(a,0,n,p);
	//maxmin(a,n-1,p1);
	/*printf("The max. element is %d and min. element is %d\n",p[0],p[1]);
	end = time(NULL);
	printf("time = %lf",difftime(end,start));*/
	return(0);
}
void maxmin(int a[],int n,int p[])
{
    int i;
    p[0]=a[0];
	p[1]=a[0];
	for(i=0;i<n;i++)
	{
		if(p[0]<a[i])
		{
			p[0]=a[i];
		}
		if(p[1]>a[i])
		{
			p[1]=a[i];
		}
	}
}
void MaxMin(int a[],int lb,int ub,int p[])
{
    int mid;
    int p1[2];
    if(lb==ub)
    {
        p[0] = p[1] = a[lb];
    }
    else if(lb == ub-1)
    {
        if(a[lb]<a[ub])
        {
            p[1] = a[lb];
            p[0] = a[ub];
        }
        else
        {
            p[0] = a[lb];
            p[1] = a[ub];
        }
    }
    else
    {
            mid = (lb + ub)/2;
            MaxMin(a,lb,mid,p);
            MaxMin(a,mid+1,ub,p1);
            if(p1[0]>p[0])
                p[0] = p1[0];
            if(p1[1]<p[1])
                p[1] = p1[1];
    }
}

