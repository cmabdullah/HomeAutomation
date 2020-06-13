#include <iostream>  // For cout
#include <stdio.h>  // standard input and output library
#include <string.h>  // contains string functions
#include <errno.h>  //It defines macros for reporting and retrieving error conditions through error codes
#include <stdlib.h>  // this includes functions regarding memory allocation
#include <wiringPi.h>
#include <sys/time.h>
#include <time.h>   //contains various functions for manipulating date and time
#include <pthread.h>

#define FAN1 6
using namespace std;
// Use GPIO Pin 18, which is Pin 0 for wiringPi library

#define BUTTON_PIN 1
// the event counter
volatile int eventCounter = 0;
volatile bool zeroCross = 0;
volatile int stepCounter = 0;

float timedifference_msec(struct timeval t0, struct timeval t1);
void setZeroCross();

void dimCheck(void);
#define FREQ_STEP 65  // This is the delay-per-brightness step in microseconds.
int dim = 128;

long long current_timestamp();
void* zeroCrossDetector(void* threadid);
void* ProcessorMajorTask(void* threadid);
void myInterrupt(void);
// -------------------------------------------------------------------------
// myInterrupt:  called every time an event occurs
void myInterrupt(void) {
  eventCounter++;
}

void* zeroCrossDetector(void* threadid) {
  long tid;
  tid = (long)threadid;
  printf("Hello Processor! Thread ID, ");

  struct timeval t0;
  struct timeval t1;
  float elapsed;
  gettimeofday(&t0, 0);

  // display counter value every second.
  while (1) {
    if (eventCounter > 0) {
      printf("%d ", eventCounter);

      gettimeofday(&t1, 0);
      elapsed = timedifference_msec(t0, t1);
      setZeroCross();
      printf("Code executed in %f milliseconds.\n", elapsed);
    } else {
      printf("%d \n", eventCounter);
      printf("%lld \n", current_timestamp());
    }
    eventCounter = 0;
    delay(1);  // wait 1ms second
  }

  pthread_exit(NULL);
}

void* ProcessorMajorTask(void* threadid) {
  long tid;
  tid = (long)threadid;
  cout << "Hello Processor! Thread ID, " << tid << endl;

  for (;;) {
    // printf ("Waiting ... ");
    delayMicroseconds(FREQ_STEP);
    dimCheck();
    fflush(stdout);
    // printf ("dimCheck called -> \n") ;
  }

  pthread_exit(NULL);
}

int main(void) {
  pthread_t zeroCrossThread;
  pthread_t processorThread;
  int processor, zeroCor, ret;

  if (wiringPiSetup() == -1) {  // when initialize wiringPi failed, print message to screen
     printf("setup wiringPi failed !\n");
    return -1;
  }

  // set Pin 18/0 generate an interrupt on high-to-low transitions
  // and attach myInterrupt() to the interrupt
  if (wiringPiISR(BUTTON_PIN, INT_EDGE_RISING, &myInterrupt) < 0) {
    fprintf(stderr, "Unable to setup ISR: %s\n", strerror(errno));
    return 1;
  }

  pinMode(FAN1, OUTPUT);

  /**start configuring processor thread with higher priority**/
  struct sched_param param;
  pthread_attr_t thread_attr;
  pthread_attr_init(&thread_attr);  // Initialise the attributes
  // SCHED_FIFO is a simple scheduling algorithm without time slicing.
  pthread_attr_setschedpolicy(&thread_attr, SCHED_FIFO);  // Set attributes to FIFO policy
  param.sched_priority = 50;
  cout << "Trying to set thread realtime prio = " << param.sched_priority << endl;

  ret = pthread_attr_setschedparam(&thread_attr, &param);  // Set attributes to priority 95

  if (ret != 0) {
    // Print the error
    printf("Unsuccessful in setting thread realtime prio");
  } else {
    printf("Successful in setting thread realtime prio");
  }

  /** processor thread configuration end **/

  printf("\n main() : creating remoteThread ");
  zeroCor = pthread_create(&zeroCrossThread, NULL, zeroCrossDetector, (void*)0);

  cout << zeroCor << endl;
  printf("\n main() : creating processorThread ");
  processor = pthread_create(&processorThread, &thread_attr, ProcessorMajorTask, (void*)1);

  if (processor != 0) {
    printf("\n processor thread create failed error");
  } else {
    printf("\n processor thread create success");
  }

  // Now verify the change in thread priority
  int policy = 0;
  ret = pthread_getschedparam(processorThread, &policy, &param);
  if (ret != 0) {
    printf("Couldn't retrieve real-time scheduling paramers");
  } else {
    printf("Retrieve real-time scheduling paramers success");
  }
  // Check the correct policy was applied
  if (policy != SCHED_FIFO) {
    printf("\nScheduling is NOT SCHED_FIFO!\n");
  } else {
    printf("\nSCHED_FIFO OK\n\n");
  }

  while (1) {
  }

  return 0;
}

void setZeroCross() {
  if (zeroCross == false) {
    zeroCross = true;
    stepCounter = 0;
    digitalWrite(FAN1, LOW);
    printf("Zero Cross set success");
  } else {
    printf("Zero Cross set already set");
  }
}

float timedifference_msec(struct timeval t0, struct timeval t1) {
  return (t1.tv_sec - t0.tv_sec) * 1000.0f + (t1.tv_usec - t0.tv_usec) / 1000.0f;
}

long long current_timestamp() {
  struct timeval te;
  gettimeofday(&te, NULL);  // get current time
  long long milliseconds = te.tv_sec * 1000LL + te.tv_usec / 1000;  // calculate milliseconds
  // printf("milliseconds: %lld\n", milliseconds);
  return milliseconds;
}

// Turn on the TRIAC at the appropriate time
void dimCheck(void) {
  if (zeroCross == true) {
    if (stepCounter >= dim) {
      digitalWrite(FAN1, HIGH);  // turn on fan
      stepCounter = 0;           // reset time step counter
      zeroCross = false;         // reset zero cross detection
      printf("Zero Cross false success");
    } else {
      stepCounter++;  // increment time step counter
    }
  }
}