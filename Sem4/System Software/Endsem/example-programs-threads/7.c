/* To compile this program use the following command */
/* $ gcc -pthread 7.c -o 7.o */

/* To run this program use the following command */
/* $ ./7.o */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

void *go1(void *ignored) {
  sleep(1);
  //pthread_join(*(pthread_t *)ignored, NULL);
  printf("1\n");
  return NULL;
}

void *go2(void *th) {
  pthread_join(*(pthread_t *)th, NULL);
  printf("2\n");
  return NULL;
}

int main() {

pthread_t one, two;

pthread_create(&one, NULL, go1, NULL);
pthread_create(&two, NULL, go2, &one);

//pthread_create(&one, NULL, go1, &two);
//pthread_create(&two, NULL, go2, NULL);

pthread_join(two, NULL);
//pthread_join(one, NULL);

return 0;
}
