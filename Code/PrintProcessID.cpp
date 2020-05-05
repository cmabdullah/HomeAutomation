#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>

int main(){
        pid_t pid, ppid;
	    gid_t gid;

	/* get the process id */
	if ((pid = getpid()) < 0) {
	  perror(" unable to get pid");
	} else {
	  printf(" The process id is %d", pid);
	}

	/* get the parent process id */
	if ((ppid = getppid()) < 0) {
	  perror("unable to get the ppid");
	} else {
	  printf("The parent process id is %d", ppid);
	}

	/* get the group process id */
	if ((gid = getgid()) < 0) {
	  perror(" unable to get the group id ");
	} else {
	  printf(" The group id is %d ", gid);
	}

	return(0);
}