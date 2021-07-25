/* To compile this program use the following command */
/* $ gcc -pthread 4.c -o 4.o */

/* To run this program use the following command */
/* $ ./4.o */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

void *foo(void *vargp) {
int id;
id = *((int *)vargp);
printf("Thread %d\n", id);
}

int main() {
pthread_t tid[3];
int i;

for (i = 0; i < 3; i++)
     pthread_create(&tid[i], NULL, foo, &i);

pthread_join(tid[0], NULL);
pthread_join(tid[1], NULL);
pthread_join(tid[2], NULL);

}
