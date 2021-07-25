/* To compile this program use the following command */
/* $ gcc -pthread 5.c -o 5.o */

/* To run this program use the following command */
/* $ ./5.o */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

void *foo(void *vargp) {
   int myid;
   myid = *((int *)vargp);
   free(vargp);
   printf("Thread %d\n", myid);
}

int main() {
pthread_t tid[3];
int i, *ptr;

for (i = 0; i < 3; i++) {
   ptr = malloc(sizeof(int));
   *ptr = i;
   pthread_create(&tid[i], NULL, foo, ptr);
}

pthread_join(tid[0], NULL);
pthread_join(tid[1], NULL);
pthread_join(tid[2], NULL);

}
