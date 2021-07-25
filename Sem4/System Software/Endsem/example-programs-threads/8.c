/* To compile this program use the following command */
/* $ gcc -pthread 8.c -o 8.o */

/* To run this program use the following command */
/* $ ./8.o */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

void *get_rand_num(void *args) {
   int *nump = malloc(sizeof(int));
   srand(pthread_self());
   *nump = rand();
   printf("Random number from thread: %d\n", *nump);
   return nump;
}

int main() 
{
pthread_t tid;
void *ptr = NULL;

pthread_create(&tid, NULL, get_rand_num, NULL);

pthread_join(tid, &ptr);

printf("Random number from main thread: %d\n", *(int *)ptr);

return 0;
}
