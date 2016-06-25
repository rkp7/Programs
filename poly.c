#include<stdio.h>
#include<stdlib.h>
struct polynode{
	int coeff;
	int expo;
	struct polynode *link;
};
void create_poly(struct polynode **p);
void display_poly(struct polynode *p);
void poly_add(struct polynode *p,struct polynode *q,struct polynode **result);
void poly_sub(struct polynode *p,struct polynode *q,struct polynode **result);
void main()
{
	struct polynode *poly1,*poly2,*add,*sub;
	poly1=poly2=add=sub=NULL;
	create_poly(&poly1);
	display_poly(poly1);
    printf("\n");
	create_poly(&poly2);
	display_poly(poly2);
	printf("\n");
	printf("Addition result:\n");
	poly_add(poly1,poly2,&add);
	display_poly(add);
    printf("\n");
	printf("Subtraction result:\n");
    poly_sub(poly1,poly2,&sub);
	display_poly(sub);
}
void create_poly(struct polynode **p)
{
	int n,i,coeff1,expo1;
	struct polynode *temp=NULL,*new_node=NULL;
	printf("Enter the no. of terms in the polynomial\n");
	scanf("%d",&n);
	for(i=1;i<=n;i++)
	{
		printf("Enter the coeff and expo of %dth term",i);
		scanf("%d%d",&coeff1,&expo1);
		new_node=(struct polynode *)malloc(sizeof(struct polynode));
		new_node->coeff=coeff1;
		new_node->expo=expo1;
		if(*p==NULL)
		{
			*p=new_node;
			temp=*p;
		}
		else
		{
			temp->link=new_node;
			temp=temp->link;
		}
	}
	temp->link=NULL;
}
void display_poly(struct polynode *p)
{
	struct polynode *temp=p;
    /*if(temp==NULL)
    printf("list empty");*/

    printf("\n polynomial:  ");
	while(temp!=NULL)
	{

		printf("%dx^%d",temp->coeff,temp->expo);
		if(temp->link!=NULL)
		{
			if(temp->link->coeff>=0)
			printf("+");


		}
		temp=temp->link;
	}

}
void poly_add(struct polynode *p,struct polynode *q,struct polynode **result)
{
	struct polynode *p1,*p2,*z,*temp;
	p1=p;
	p2=q;
	while(p1!=NULL&&p2!=NULL)
	{
		z=(struct polynode *)malloc(sizeof(struct polynode));
		if(p1->expo > p2->expo)
		{
			z->expo=p1->expo;
			z->coeff=p1->coeff;
			p1=p1->link;
		}
		else if(p1->expo<p2->expo)
			{
				z->expo=p2->expo;
				z->coeff=p2->coeff;
				p2=p2->link;
			}
			else
			{
				z->coeff=p1->coeff+p2->coeff;
				z->expo=p1->expo;
				p1=p1->link;
				p2=p2->link;
			}
		if(*result==NULL)
		{
			*result=z;
			temp=z;
		}
		else
		{
			temp->link=z;
			temp=temp->link;
		}
	}
	while(p1!=NULL)
	{

			z=(struct polynode *)malloc(sizeof(struct polynode));

				z->coeff=p1->coeff;
				z->expo=p1->expo;


			if(*result==NULL)
			{
				*result=z;
				temp=z;
			}
			else
			{
				temp->link=z;
				temp=temp->link;
			}
				p1=p1->link;

	}


    while(p2!=NULL)
    {

        z=(struct polynode *)malloc(sizeof(struct polynode));
        z->coeff=p2->coeff;
        z->expo=p2->expo;
        if(*result==NULL)
        {
            *result=z;
            temp=z;
        }
        else
        {
            temp->link=z;
            temp=temp->link;
        }
        p2=p2->link;
	}
	temp->link=NULL;
}
void poly_sub(struct polynode *p,struct polynode *q,struct polynode **result)
{
	struct polynode *p1,*p2,*z,*temp;
	p1=p;
	p2=q;
	while(p1!=NULL&&p2!=NULL)
	{
		z=(struct polynode *)malloc(sizeof(struct polynode));
		if(p1->expo > p2->expo)
		{
			z->expo=p1->expo;
			z->coeff=p1->coeff;
			p1=p1->link;
		}
		else if(p1->expo<p2->expo)
			{
				z->expo=p2->expo;
				z->coeff=p2->coeff;
				p2=p2->link;
			}
			else
			{
				z->coeff=p1->coeff-p2->coeff;
				z->expo=p1->expo;
				p1=p1->link;
				p2=p2->link;
			}
		if(*result==NULL)
		{
			*result=z;
			temp=z;
		}
		else
		{
			temp->link=z;
			temp=temp->link;
		}
	}
	while(p1!=NULL)
	{

			z=(struct polynode *)malloc(sizeof(struct polynode));

				z->coeff=p1->coeff;
				z->expo=p1->expo;


			if(*result==NULL)
			{
				*result=z;
				temp=z;
			}
			else
			{
				temp->link=z;
				temp=temp->link;
			}
				p1=p1->link;

	}


    while(p2!=NULL)
    {

        z=(struct polynode *)malloc(sizeof(struct polynode));
        z->coeff=p2->coeff;
        z->expo=p2->expo;
        if(*result==NULL)
        {
            *result=z;
            temp=z;
        }
        else
        {
            temp->link=z;
            temp=temp->link;
        }
        p2=p2->link;
	}
	temp->link=NULL;
}





