#include<stdio.h>
#include<stdlib.h>
#define MAX 30
struct tree
{
	char data;
	struct tree *left;
	struct tree *right;
};
char a[MAX];
struct tree* buildtree(int n)
{
	struct tree *temp=NULL,*s;
	int i=0;	
	if(a[n]!='X')
	{
		temp=(struct tree*)malloc(sizeof(struct tree));
		temp->data=a[n];
		temp->left=buildtree(2 * n + 1);
		temp->right=buildtree(2*n+2);
	}
	return(temp);
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
	struct tree *t=NULL;
	int choice;
	do
	{
		printf("\nEnter choice:\n1-Build Tree\n2-Inorder traversal\n3-Preorder traversal");
		printf("\n4-postorder\n5-exit\n");
		scanf("%d",&choice);
		switch(choice)
		{
			case 1 : printf("Enter the elements of tree and finally press new line:\n");
				 scanf("%s",a);
				 t=buildtree(0);
				 break;
		
			case 2 : printf("inorder traversal is:\n"); 
				 inorder(t);
				 break;
			case 3 : printf("Preorder traversal is:\n");
				 preorder(t);
				 break;
			case 4 : printf("Postorder traversal is:\n");
				 postorder(t);
				 break;
		}
	}
	while(choice!=5);
}

