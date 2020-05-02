#include<iostream>
#include<cstdlib>
#include<pthread.h>
#include<sched.h>

using namespace std;
///https://www.raspberrypi.org/forums/viewtopic.php?t=242590
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
  pthread_attr_setschedpolicy(&thread_attr, SCHED_RR);  // Set attributes to RR policy
  param.sched_priority = 95;
  pthread_attr_setschedparam(&thread_attr, &param); // Set attributes to priority 95

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

  cout << "\nmain() : creating thread, Remot5 " << endl;
  remot5 = pthread_create(&threads[5], NULL, Remot5, (void *)5);

  pthread_exit(NULL);
}

// g++ -o a.out concurrentCmSCHED_RR.cpp && ./a.out
//g++ -o a.out concurrentCmSCHED_RR.cpp -lpthread && ./a.out
