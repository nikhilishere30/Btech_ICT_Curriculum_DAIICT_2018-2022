/* To compile this program use the following command */
/* $ gcc -pthread 6.c -o 6.o */

/* You may get warnings due to type cast - but you could still run the program */

/* To compile this program supressing warnings use the following command */
/* $ gcc -pthread -Wno-int-to-pointer-cast -Wno-pointer-to-int-cast 6.c -o 6.o */

/* To run this program use the following command */
/* $ ./6.o */

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

void *foo(void *vargp) {
  int id;
    id = (int)vargp;
//  id = *((int *) vargp);           // Uncomment this line to avoid warnings and comment line 18 (above) - compile using line 2 command
  printf("Thread %d\n", id);
}

int main() {
pthread_t tid[3];
int i;
void *pointer = &i;       // extra pointer variable to avoid warnings and adding the needed type cast

for (i = 0; i < 3; i++)
    pthread_create(&tid[i], NULL, foo, (void *)i);
//  pthread_create(&tid[i], NULL, foo, pointer); // Uncomment this line to avoid warnings and comment line 29 (above) - compile using line 2 command

pthread_join(tid[0], NULL);
pthread_join(tid[1], NULL);
pthread_join(tid[2], NULL);

}
