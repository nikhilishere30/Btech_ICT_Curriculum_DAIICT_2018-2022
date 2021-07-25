/* To compile this program use the following command */
/* $ gcc -pthread 3.c -o 3.o */

/* To run this program use the following command */
/* $ ./3.o */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

/* thread routine */

void *mythread(void *vargp) {
  printf("Thread.a\n");
  printf("Thread.b\n");
return NULL;
}

int main() {
pthread_t tid;
 
printf("Main.a\n");

pthread_create(&tid, NULL, mythread, NULL);

printf("Main.b\n");
printf("Main.c\n");

pthread_join(tid, NULL);

printf("Main.d\n");

exit(0);
}
