#include<iostream>
#include<cstdlib>
#include<pthread.h>
#include<sched.h>

using namespace std;
//https://news.ycombinator.com/item?id=21926563
//https://stackoverflow.com/questions/20722615/sched-fifo-process-with-priority-of-99-gets-preempted
//https://lwn.net/Articles/296419/
//https://stackoverflow.com/questions/31404770/unable-to-change-thread-policy-to-sched-fifo
#define NUM_THREADS 6
volatile int stepCounter = 0;  
void *Processor(void *threadid) {
  long tid;
  int k = 0;
  tid = (long)threadid;
  cout << "Hello Processor! Thread ID, " << tid << endl;

  for (k = 0 ; k< 20;k++){
    stepCounter++;
    printf("processor thread %d stepCounter %d \n", k, stepCounter);
  }

  pthread_exit(NULL);
}

void *Remot1(void *threadid) {
  long tid;
  int j = 0;
  tid = (long)threadid;
  cout << "\nHello Remot 1! Thread ID, " << tid << endl;

  for (j = 0 ; j< 20;j++){
    stepCounter++;
    printf("Remot 1 thread %d stepCounter %d \n", j, stepCounter);
  }
  pthread_exit(NULL);
}

void *Remot2(void *threadid) {
  long tid;
  int j = 0;
  tid = (long)threadid;
  cout << "\nHello Remot2 ! Thread ID, " << tid << endl;

  for (j = 0 ; j< 20;j++){
    stepCounter++;
    printf("Remot 2 thread %d stepCounter %d \n", j, stepCounter);
  }
  pthread_exit(NULL);
}

void *Remot3(void *threadid) {
  long tid;
  int j = 0;
  tid = (long)threadid;
  cout << "\nHello Remot 3 ! Thread ID, " << tid << endl;

  for (j = 0 ; j< 20;j++){
    stepCounter++;
    printf("Remot 3 thread %d stepCounter %d \n", j, stepCounter);
  }
  pthread_exit(NULL);
}

void *Remot4(void *threadid) {
  long tid;
  int j = 0;
  tid = (long)threadid;
  cout << "\nHello Remot 4 ! Thread ID, " << tid << endl;

  for (j = 0 ; j< 20;j++){
    stepCounter++;
    printf("Remot 4 thread %d stepCounter %d \n", j, stepCounter);
  }
  pthread_exit(NULL);
}

void *Remot5(void *threadid) {
  long tid;
  int j = 0;
  tid = (long)threadid;
  cout << "\nHello Remot 5 ! Thread ID, " << tid << endl;

  for (j = 0 ; j< 20;j++){
    stepCounter++;
    printf("Remot 5 thread %d stepCounter %d \n", j, stepCounter);
  }
  pthread_exit(NULL);
}

int main () {
  pthread_t threads[NUM_THREADS];
  int processor, remot1, remot2,remot3,remot4,remot5;
  int i;
  int ret;

  pthread_t processorThread;

  struct sched_param  param;
  pthread_attr_t thread_attr;
  pthread_attr_init(&thread_attr);  // Initialise the attributes

  //SCHED_FIFO is a simple scheduling algorithm without time slicing.
  pthread_attr_setschedpolicy(&thread_attr, SCHED_FIFO);  // Set attributes to FIFO policy
  param.sched_priority = 90;
  std::cout << "Trying to set thread realtime prio = " << param.sched_priority << std::endl;

  ret = pthread_attr_setschedparam(&thread_attr, &param); // Set attributes to priority 95

  if (ret != 0) {
    // Print the error
    std::cout << "Unsuccessful in setting thread realtime prio" << std::endl;
  } else{
    std::cout << "Successful in setting thread realtime prio" << std::endl;
  }


  cout << "\nmain() : creating thread, Remot 1" << endl;
  remot1 = pthread_create(&threads[1], NULL, Remot1, (void *)1);

  cout << "\nmain() : creating thread, Remot2 " << endl;
  remot2 = pthread_create(&threads[2], NULL, Remot2, (void *)2);

  cout << "\nmain() : creating thread, Remot3 " << endl;
  remot3 = pthread_create(&threads[3], NULL, Remot3, (void *)3);

  cout << "\nmain() : creating thread, Remot4 " << endl;
  remot4 = pthread_create( &threads[4], NULL, Remot4, (void *)4);

  cout << "\nmain() : creating thread, Processor " << endl;
  processor = pthread_create(&processorThread, &thread_attr, Processor, (void *)6);

  if (processor != 0){
    printf("\nprocessor thread create failed error");
  } else{
    printf("\nprocessor thread create success : ");
  }

  // Now verify the change in thread priority
  int policy = 0;
  ret = pthread_getschedparam(processorThread, &policy, &param);
    if (ret != 0) {
      std::cout << "Couldn't retrieve real-time scheduling paramers" << std::endl;
    } else {
      std::cout << "Retrieve real-time scheduling paramers success" << std::endl;
    }
  // Check the correct policy was applied
  if(policy != SCHED_FIFO) {
    std::cout << "\nScheduling is NOT SCHED_FIFO!\n" << std::endl;
  } else {
    std::cout << "\nSCHED_FIFO OK\n\n" << std::endl;
  }

  cout << "\nmain() : creating thread, Remot5 " << endl;
  remot5 = pthread_create(&threads[5], NULL, Remot5, (void *)5);

  pthread_exit(NULL);
}

// g++ -o a.out concurrentCmSCHED_RR.cpp && ./a.out
//g++ -o a.out concurrentCmSCHED_RR.cpp -lpthread && ./a.out

/****

SCHED_FIFO and SCHED_RR are so called "real-time" policies.
SCHED_FIFO and SCHED_RR are so called "real-time" policies. They implement the fixed-priority real-time scheduling specified by the POSIX standard.
Tasks with these policies preempt every other task, which can thus easily go into starvation (if they don't release the CPU).
**/
