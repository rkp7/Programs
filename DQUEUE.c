#include<stdio.h>
#include<stdlib.h>
#define MAX 30
struct queue{
	int data[MAX];
	int front,rear;
};
void init(struct queue *q)
{
	q->front=-1;
	q->rear=-1;
}
int isempty(struct queue *q)
{
	if(q->rear==-1)
		return(1);
	else
		return(0);
}
int full(struct queue *q)
{
	if(q->rear+q->front==MAX-1)
	{
		printf("Queue is full\n");
		return(1);
	}
	else
	return(0);
}
void display(struct queue *q)
{
	int i;
	if(q->rear==-1)
	{
	    printf("Queue is empty\n");
	}
	else
	{
        printf("The elements of the queue are:\n");
        for(i=q->front;i<=q->rear;i++)
        {
            printf("%d\t",q->data[i]);
        }
        printf("\n");
	}
}
void insert_front(struct queue *q,int ele)
{
    int temp=0,i=q->rear+1;
    if(full(q))
    {
        printf("the queue is full\n");
    }
    else    if(isempty(q))
            {
                q->front=q->rear=0;
                q->data[q->front]=ele;
            }
            else
            {
                while(i>=q->front)
		{
                	q->data[i]=q->data[i-1];
			i--;
		}
		q->data[q->front]=ele;
		q->rear=q->rear+1;
            }
}
void insert_rear(struct queue *q,int ele)
{
    if(full(q))
    {
        printf("The queue is full\n");
    }
    else    if(isempty(q))
            {
                q->front=q->rear=0;
                q->data[q->rear]=ele;
            }
            else
            {
                q->rear++;
                q->data[q->rear]=ele;
            }
}
int delete_rear(struct queue *q)
{
    int c=0;
    if(isempty(q))
    {
        printf("Queue empty\n");
    }
    else
    {
            c=q->data[q->rear];
            q->data[q->rear]=0;
            if(q->rear==q->front)
            {
               init(q);
            }
            else
            {
                q->rear--;
            }
    }
    return(c);
}
int delete_front(struct queue *q)
{
    int c=0;
    if(isempty(q))
    {
        printf("Queue empty\n");
    }
    else
    {
            c=q->data[q->front];
            q->data[q->front]=0;
            if(q->rear==q->front)
            {
               init(q);
            }
            else
            {
                q->front++;
            }
    }
    return(c);
}
int main()
{
	int choice;
	int ele;
	struct queue r;
	init(&r);
	do{
        printf("Enter your choice:\n1-insert at front\n2-insert at rear\n3-delete front\n");
        printf("4-delete rear\n5-display\n6-exit\n");
        scanf("%d",&choice);
        switch(choice)
        {
            case 1 :printf("Enter the element to be inserted:\n");
                    scanf("%d",&ele);
                    insert_front(&r,ele);
                	  break;
            case 2 :printf("Enter the element to be inserted:\n");
                    scanf("%d",&ele);
                    insert_rear(&r,ele);
                	  break;
            case 3 : ele=delete_front(&r);
                 	   printf("The deleted element is %d \n",ele);
                 	   break;
            case 4 : ele=delete_rear(&r);
                 	   printf("The deleted element is %d \n",ele);
                 	   break;
            case 5 : display(&r);
        }
	}
	while(choice!=6);
	return(0);
}
