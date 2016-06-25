#include<stdio.h>
#define MAX 10
struct stack
{
	int data[MAX];
	int top;
};
void push(struct stack *s,int num)
{
	if(s->top==MAX-1)
		printf("stack full ");
	else
	    {
			s->top++;	
			s->data[s->top]=num;
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
		n=s->data[s->top];
		s->top--;
	}
	return(n);
}
int peep(struct stack *s)
{
	if(s->top==-1)
	{
		printf("Stack empty ");
	}
	else
	{	
		return(s->data[s->top]);
	}
}
int evaluate(char postfix[])
{
	struct stack s1;
        
	int i=0,j,k,num,t,res,n,m;
        init(&s1);
	while(postfix[i]!='\0')
	{
		if(isdigit(postfix[i]))
		{
			num=postfix[i]-'0';				
			push(&s1,num);
		}
		else
		{
			j=pop(&s1);
			k=pop(&s1);
			switch(postfix[i])
			{
				case   '+' : push(&s1,(k+j));
						break;
				case   '-' :  push(&s1,(k-j));
						break;
				case   '*' :  push(&s1,(k*j));
						break;
				case   '/' :  push(&s1,(k/j));
						break;
				case   '$' : 	res=k;
						for(m=1;m<j;m++)
						{
							res=res*k;
						}
						push(&s1,res);
						break;
				default    :  printf("incompatible symbol ");
			}
		}	
	i++;
	}
	return(pop(&s1));
}
		
int isempty(struct stack s)
{
	if(s.top==-1)
		return(1);
	else
		return(0);
}
int priority(char c)
{
	switch(c)
	{
		case '$' : return(3);
				break;
		case '*' :
		case '/' : return(2);
				break;
		case '+' : 
		case '-' : return(1);
				break;
		case '(' : return(0);
				break;
		default  : printf("Invalid operator");
	}
}	

void infixToPostfix(char infix[],char postfix[])
{
	struct stack s1;
	int i=0,j=0,r;
	char ch,c;
	init(&s1);
	while(infix[i]!='\0')
	{
		ch=infix[i];
		if(isdigit(ch))
		{
			postfix[j++]=ch;
		}
		else	if(ch==')')
			{
				c=pop(&s1);
				while(c!='(')
				{
					postfix[j++]=c;
					c=pop(&s1);
				}
			}
			else	if(ch=='(')
				{
					push(&s1,ch);
				}
				else
				{
					
					while(!isempty(s1)&&
					priority(ch)<=priority(peep(&s1)) )
					{							
						c=pop(&s1);
						printf("pop operator %c\n",c);
						postfix[j++]=c;

					}
					printf("push operator %c\n",ch);
					push(&s1,ch);
				}
		i++;
	}
	while(!isempty(s1))
	{
		c=pop(&s1);
		postfix[j++]=c;
	}
	postfix[j]='\0';
}	
		
	
void main()
{
	int i,j,t;
	struct stack s;
	char post[20],infix[20];
	init(&s);
	push(&s,'5');
	push(&s,'2');
	i=pop(&s);
	j=peep(&s);
	if(s.top!=-1)
	{
		printf("Popped element = %d\n ",i);
		printf("Top element = %d\n ",j);
	}
	printf("Enter the infix expression:\n");
	scanf("%s",infix);
	infixToPostfix(infix,post);
	printf("Postfix statement:\n");
	printf("%s",post);
	/*printf("Enter postfix expression:\n ");
	scanf("%s",post);*/
	t=evaluate(post);
	printf("\nThe result is %d ",t);

}
			
