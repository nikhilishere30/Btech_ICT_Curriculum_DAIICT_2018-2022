#include <unistd.h>
#include <sys/types.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <signal.h>

main()
{
	pid_t pid;
	char cmd[20];
	pid = getpid();

	if (fork() == 0)
		{
			if (fork() == 0)
				while(1);
			while(1);
		}
	if (fork() == 0)
		{
			if (fork() == 0)
			while(1);

			if (fork() == 0)
				while(1);
		
			while(1);
		}

	if (fork() == 0)
	while(1);

	getc(stdin);
	sprintf(cmd, "pstree -p %d\n", pid);
	system(cmd);
	kill(pid, SIGKILL);
}
