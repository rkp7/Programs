//A2-1211026

#include<stdio.h>
#define MAX 20

void tt(int cn[],float p[],float sr[],int l,int h,int n,int r[]);
void check2(float p[],float sr[],int *a,int *b,int *c);
void check1(float p[],float sr[],int *a,int *b);
void display_win(int x,int y,int z,int i);
char a[MAX][MAX];

int main()
{
    int n,i,j,r[2];
    int cn[MAX];
    float p[MAX],sr[MAX];
    printf("Enter the number of players:");
    scanf("%d",&n);
    for(i=0;i<n;i++)
    {
        printf("\nEnter the name of contestant %d: ",i+1);
        scanf("%s",a[i]);
        printf("Enter probability of winning(0 to 1) and skill rating(0 to 10):");
        scanf("%f %f",&p[i],&sr[i]);
        cn[i] = i + 1;
    }
    printf("\nThe data entered is : \n\n");
    printf("NAME\tPOW\tSR\n");
    for(i=0;i<n;i++)
    {
        printf("%s\t%.2f\t%.2f\n",a[i],p[i],sr[i]);
    }
    tt(cn,p,sr,0,n-1,n,r);
    printf("\n\nThe winner is %s and runners up is %s",a[r[0]],a[r[1]]);
    return(0);
}

void tt(int cn[],float p[],float sr[],int l,int h,int n,int r[])
{
    int c,c1,cf,r1[2];
    if(h == l)
    {
        r[0] = r[1] =h;
    }
    else if(h==l+1)
    {
        if(p[h]>p[l])
        {
            r[0] = h;
            r[1] = l;
        }
        else if(p[h]<p[l])
        {
            r[0] = l;
            r[1] = h;
        }
        else
        {
            if(sr[h]>sr[l])
            {
                r[0] = h;
                r[1] = l;
            }
            else
            {
                r[0] = l;
                r[1] = h;
            }
        }
        display_win(r[0],r[1],r[0],1);
    }
    else
    {
        tt(cn,p,sr,l,(l+h)/2,n,r);
        tt(cn,p,sr,(l+h)/2 + 1,h,n,r1);
        if(p[r1[0]]>p[r[0]])
        {
            check2(p,sr,&r1[1],&r[0],&r[1]);
            display_win(r[0],r1[0],r1[0],1);
            display_win(r1[1],r[0],r[1],0);
            r[0]=r1[0];
        }
        else if(p[r1[0]]==p[r[0]])
        {
            if(sr[r1[0]]>sr[r[0]])
            {
                check2(p,sr,&r1[1],&r[0],&r[1]);
                display_win(r[0],r1[0],r1[0],1);
                display_win(r1[1],r[0],r[1],0);
                r[0] = r1[0];
            }
            else
            {
                check1(p,sr,&r1[0],&r[1]);
                display_win(r[0],r1[0],r[0],1);
                display_win(r1[0],r[1],r[1],0);
            }

        }
        else
        {
            check1(p,sr,&r1[0],&r[1]);
            display_win(r[0],r1[0],r[0],1);
            display_win(r1[0],r[1],r[1],0);
        }
    }
}

void check1(float p[],float sr[],int *a,int *b)
{
    if(p[*a]>p[*b])
            {
                *b=*a;
            }
            else if(p[*a]==p[*b])
            {
                if(sr[*a]>sr[*b])
                {
                    *b = *a;
                }
            }
}

void check2(float p[],float sr[],int *a,int *b,int *c)
{
            if(p[*a]>p[*b])
            {
                *c=*a;
            }
            else if(p[*a]==p[*c])
            {
                if(sr[*a]>sr[*c])
                {
                    *c = *a;
                }
            }
            else
            {
                *c = *b;
            }
}

void display_win(int x,int y,int z,int i)
{
    if(i==1)
    {
        printf("\n\nFor winning position, match between %s and %s is won by %s",a[x],a[y],a[z]);
    }
    if(i==0 && x!=y)
    {
        printf("\n\nFor runners up position, match between %s and %s is won by %s",a[x],a[y],a[z]);
    }
}

/*

Output:

Enter the number of players:8

Enter the name of contestant 1: raj
Enter probability of winning(0 to 1) and skill rating(0 to 10):0.9 7

Enter the name of contestant 2: kewal
Enter probability of winning(0 to 1) and skill rating(0 to 10):0.7 8

Enter the name of contestant 3: jay
Enter probability of winning(0 to 1) and skill rating(0 to 10):0.9 5

Enter the name of contestant 4: meet
Enter probability of winning(0 to 1) and skill rating(0 to 10):0.6 7

Enter the name of contestant 5: rishit
Enter probability of winning(0 to 1) and skill rating(0 to 10):0.5 5

Enter the name of contestant 6: shrenik
Enter probability of winning(0 to 1) and skill rating(0 to 10):0.5 4

Enter the name of contestant 7: mihir
Enter probability of winning(0 to 1) and skill rating(0 to 10):0.4 4

Enter the name of contestant 8: ashwin
Enter probability of winning(0 to 1) and skill rating(0 to 10):0.6 8

The data entered is :

NAME    POW     SR
raj     0.90    7.00
kewal   0.70    8.00
jay     0.90    5.00
meet    0.60    7.00
rishit  0.50    5.00
shrenik 0.50    4.00
mihir   0.40    4.00
ashwin  0.60    8.00


For winning position, match between raj and kewal is won by raj

For winning position, match between jay and meet is won by jay

For winning position, match between raj and jay is won by raj

For winning position, match between rishit and shrenik is won by rishit

For winning position, match between ashwin and mihir is won by ashwin

For winning position, match between rishit and ashwin is won by ashwin

For runners up position, match between mihir and rishit is won by rishit

For winning position, match between raj and ashwin is won by raj

For runners up position, match between ashwin and jay is won by jay

The winner is raj and runners up is jay

*/
