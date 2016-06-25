#include<stdio.h>
#include<stdlib.h>
#define MAX 30
struct stack
{
	int arr[MAX];
	int top;
};
void push(struct stack *s,int num)
{
	if(s->top==MAX-1)
		printf("stack full ");
	else
	    {
			s->top++;	
			s->arr[s->top]=num;
	    }
}
void init(struct stack *s)
{
	
	s->top=-1;
}
int pop(struct stack *s)
{
	int n;
	if(s->top==-1)
	{
		printf("Stack empty ");
	}
	else
	{
		n=s->arr[s->top];
		s->top--;
	}
	return(n);
}
void display(struct stack s)
{
	int x;
	x=s.top;
	while(x>=0)
	{
		printf("%d\t",s.arr[x]);
		x--;
	}
}
int isempty(struct stack s)
{
	if(s.top==-1)	
		return(1);
	else
		return(0);
}			
int factorial(int n)
{	
	struct stack s;
	init(&s);
	int retaddr,param,addr,fact;
	retaddr=100;
	param=n;
	addr=20;
	push(&s,param);
	push(&s,retaddr);
	while(!isempty(s))
	{
		switch(addr)
		{
			case 20 : if(param==0)
				  {
					fact=1;	
				  	addr=40;
				  }
				  else
				  {
	   				param=param-1;
					push(&s,param);
					push(&s,30);
					addr=20;
				   }
				   break;
			case 30 : fact=fact*(param+1);
			
			case 40 : addr=pop(&s);
				  param=pop(&s);
		}
	printf("\nstack contents are \n");
	display(s);
	}
	return(fact);
}
void main()
{
	int x,y;
	printf("enter a no.\n");
	scanf("%d",&x);
	y=factorial(x);
	printf("factorial is = %d\n",y);
}
						
		
