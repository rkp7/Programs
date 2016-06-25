#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#define MAX 30
struct stack
{
	struct tree *arr[MAX];
	int top;
};
struct tree
{
	char data;
	struct tree *left;
	struct tree *right;
};

void push(struct stack *s,struct tree *t)
{
	if(s->top==MAX-1)
		printf("stack full\n");
	else
	{
		s->top++;
		s->arr[s->top]=t;
	}
}
void init(struct stack *s)
{
	s->top=-1;
}
struct tree* pop(struct stack *s)
{
	struct tree *p;	
	if(s->top==-1)
	{
		printf("Stack empty");
	}
	else
	{
		p=s->arr[s->top--];
	}
		return(p);
}
struct tree* buildtree(char postfix[])
{
	struct tree *temp,*t;
	struct stack s;

	int i;
init(&s);

	for( i=0;postfix[i]!='\0';i++)
	{
		if(isalnum(postfix[i]))
		{
			temp=(struct tree*)malloc(sizeof(struct tree));
			temp->data=postfix[i];
			temp->left=temp->right=NULL;
			push(&s,temp);
		}
		else
		{
			temp=(struct tree*)malloc(sizeof(struct tree));
			temp->data=postfix[i];
			temp->right=pop(&s);
			temp->left=pop(&s);
			push(&s,temp);
		}
	}
	t=pop(&s);
	return(t);
}
int eval(struct tree *t)
{
	int m,res;	

	if(t!=NULL)
	{
		switch(t->data)
		{
			case '+' : return(eval(t->left)+eval(t->right));
					break;
			case '-' : return(eval(t->left)-eval(t->right));
					break;
			case '*' : return(eval(t->left)*eval(t->right));
					break;	
			case '/' : return(eval(t->left)/eval(t->right));
					break;
			case '$' : 	res=eval(t->left);
					for(m=1;m<eval(t->right);m++)
						res=res*eval(t->left);
					return(res);
					break;
			default: return(t->data-48);
		}
	}
}					
void inorder(struct tree *t)
{	
	if(t!=NULL)
	{
		inorder(t->left);
		printf("%c\t",t->data);
		inorder(t->right);
	}
}
void preorder(struct tree*t)
{
	if(t!=NULL)
	{
		printf("%c\t",t->data);
		preorder(t->left);
		preorder(t->right);
	}
}
void postorder(struct tree *t)
{
	if(t!=NULL)
	{
		postorder(t->left);
		postorder(t->right);
		printf("%c\t",t->data);
	}
}
void main()
{
	char postfix[30];
	int res;
	struct tree *t;
	printf("Enter the postfix statement:\n");
	scanf("%s",postfix);
	t=buildtree(postfix);
	inorder(t);
	res=eval(t);
	printf("result is=%d\n",res);
}

