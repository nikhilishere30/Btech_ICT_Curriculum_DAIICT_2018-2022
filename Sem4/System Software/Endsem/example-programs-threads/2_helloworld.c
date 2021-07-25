/* To compile this program use the following command */
/* $ gcc -pthread 2_helloworld.c -o 2_helloworld.o */

/* To run this program use the following command */
/* $ ./2_helloworld.o */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>


/* thread routine */

void *thread(void *vargp) {
printf("Hello, world!\n");
return NULL;
}

int main() {
pthread_t tid;
pthread_create(&tid, NULL, thread, NULL);
printf("Hello from the main thread\n");
pthread_join(tid, NULL);
exit(0);
}
