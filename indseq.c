#include<stdio.h>
int arr[]={1,2,4,5,8,22,25,36,92,100};
struct index{
	int key;
	int pindex;
};
struct index i[3];
int ind_seq_search(int k)
{
	int m,j;
	int lowlim,hilim;
	for(m=0;m<3&&i[m].key<=k;m++);
	
		lowlim=(m==0)?0:i[m-1].pindex;
		hilim=(m==3)?9:i[m].pindex-1;
		for(j=lowlim;j<=hilim&&arr[j]!=k;j++);
			return((j>hilim)?-1:j);
	
}
void main()
{
	int ele,ind;	
	i[0].key=1;
	i[0].pindex=0;
	i[1].key=8;
	i[1].pindex=4;
	i[2].key=36;
	i[2].pindex=7;
	printf("Enter the element to be searcched:\n");
	scanf("%d",&ele);
	ind=ind_seq_search(ele);
	if(ind==-1)
	{
		printf("Element not found");
	}
	else
	{
		printf("Element found at location %d\n",ind);
	}
}	
