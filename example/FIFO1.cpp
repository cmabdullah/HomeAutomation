#include<sched.h>
#include<time.h>
#include <iostream>
#include <pthread.h>
using namespace std;
//http://www.yonch.com/tech/82-linux-thread-priority
//https://raspberrypi.stackexchange.com/questions/8808/zero-crossing-activated-relay
//https://www.princeton.edu/~cad/emsim/files/example.c
//http://man7.org/linux/man-pages/man3/pthread_setschedparam.3.html
//https://stackoverflow.com/questions/3649281/how-to-increase-thread-priority-in-pthreads
//https://en.m.wikipedia.org/wiki/Native_POSIX_Thread_Library
//You don't need to hack the kernel. You just need to move the process out of the scheduler queue.
void set_realtime_priority() {

   int ret;
   int j = 0;
   // We'll operate on the currently running thread.
   pthread_t this_thread = pthread_self();

   // struct sched_param is used to store the scheduling priority
   struct sched_param params;

   // We'll set the priority to the maximum.
   params.sched_priority = sched_get_priority_max(SCHED_FIFO);

   std::cout << "Trying to set thread realtime prio = " << params.sched_priority << std::endl;

   // Attempt to set thread real-time priority to the SCHED_FIFO policy
   ret = pthread_setschedparam(this_thread, SCHED_FIFO, &params);
   if (ret != 0) {
      // Print the error
      std::cout << "Unsuccessful in setting thread realtime prio" << std::endl;
      return;
   }

   // Now verify the change in thread priority
   int policy = 0;
   ret = pthread_getschedparam(this_thread, &policy, &params);
   if (ret != 0) {
      std::cout << "Couldn't retrieve real-time scheduling paramers" << std::endl;
      return;
   }

   // Check the correct policy was applied
   if(policy != SCHED_FIFO) {
      std::cout << "Scheduling is NOT SCHED_FIFO!" << std::endl;
   } else {
      std::cout << "SCHED_FIFO OK" << std::endl;
   }

   for (j = 0 ; j < 10; j++){
      printf("fifo thread %d \n" , j );
   }

   // Print thread scheduling priority
   std::cout << "Thread priority is " << params.sched_priority << std::endl;
}

int main () {

   int i = 0;

   printf("thread main\n");
   set_realtime_priority();

   for (i = 0 ; i < 10; i++){
      printf("main thread %d \n" , i );
   }

   printf("thread main end\n");
}

// g++ test.cpp  -lpthread
/****
$ chrt -m
SCHED_OTHER min/max priority    : 0/0
SCHED_FIFO min/max priority     : 1/99
SCHED_RR min/max priority       : 1/99
SCHED_BATCH min/max priority    : 0/0
SCHED_IDLE min/max priority     : 0/0

alias batchmake='time chrt --batch 0 make --silent'

*/