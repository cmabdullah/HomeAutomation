#include<iostream>
#include<cstdlib>
#include<pthread.h>
#include<sched.h>
#include <stdio.h>
#include <stdlib.h>
#include<string.h>
#include<errno.h>
#include<wiringPi.h>

using namespace std;

// What GPIO input are we using?
// This is a wiringPi pin number
#define FAN_PIN 3   // Output to Opto Triac
#define INTERRUPT_PIN 2 //#define directive allows the definition of macros within your source code
#define FREQ_STEP 75   // This is the delay-per-brightness step in microseconds.
#define PIN_HIGH 1 //1 == HIGH
#define PIN_LOW 0 //2 == LOW

volatile boolean zeroCross=0;
// Boolean to store a "switch" to tell us if we have crossed zero
volatile int stepCounter = 0;  
// Should be declared volatile to make sure the compiler doesn't cache it.

int dim = 128;  // Dimming level (0-128)  0 = on, 128 = 0ff
int pas = 14;   // step for count;

void zeroCrossDetect(void);
void dimCheck(void);
void *ProcessorMajorTask(void *threadid);
void *Remote(void *threadid);

void *ProcessorMajorTask(void *threadid) {
  long tid;
  int k = 0;
  tid = (long)threadid;
  cout << "Hello Processor! Thread ID, " << tid << endl;

  for (;;) {
    printf ("Waiting ... ");
    delayMicroseconds(FREQ_STEP);
    dimCheck();
    fflush (stdout) ;
    printf ("dimCheck called -> \n") ;
  }

  pthread_exit(NULL);
}

void *Remote(void *threadid) {
  long tid;
  int j = 0;
  int num;
  FILE *fptr;
  tid = (long)threadid;
  cout << "\nHello Remot ! Thread ID, " << tid << endl;

  for (;;) {
    printf ("Waiting ... ");
    delay(1000);//1000ms
    printf("Remot thread \n");

   if ((fptr = fopen("test.txt","r")) == NULL) {       // checks if file exists
       puts("File not exists");
       exit(1);                    // for exit(1) is required #include <stdlib.h>
   } else{
      fscanf(fptr,"%d", &num);
      printf("Remote parameter is:  %d\n", num);
      fclose(fptr);
      printf("stepCounter is:  %d\n", stepCounter);

      if (dim < 0){
        dim = 0;
      } else if (dim > 128){
        dim = 128;
      } else{
            dim = dim+num;
      }
      printf("agter read from file stepCounter new value is:  %d\n", stepCounter);
   }
  }
  pthread_exit(NULL);
}

int main () {
  pthread_t remoteThread;
  pthread_t processorThread;
  int processor, remoteFlag, ret;

  //configuring basic setup, pin, interrupt,

  if (wiringPiSetup () < 0) {
    fprintf (stderr, "Unable to setup wiringPi: %s\n", strerror (errno)) ;
    return 1 ;
  }

  pinMode(FAN_PIN, OUTPUT);  // Set the Triac pin as output

  if (wiringPiISR (INTERRUPT_PIN, INT_EDGE_RISING, &zeroCrossDetect) < 0) {
    fprintf (stderr, "Unable to setup ISR: %s\n", strerror (errno)) ;
    return 1 ;
  }

  /**start configuring processor thread with higher priority**/
  struct sched_param  param;
  pthread_attr_t thread_attr;
  pthread_attr_init(&thread_attr);  // Initialise the attributes
  //SCHED_FIFO is a simple scheduling algorithm without time slicing.
  pthread_attr_setschedpolicy(&thread_attr, SCHED_FIFO);  // Set attributes to FIFO policy
  param.sched_priority = 50;
  std::cout << "Trying to set thread realtime prio = " << param.sched_priority << std::endl;

  ret = pthread_attr_setschedparam(&thread_attr, &param); // Set attributes to priority 95

  if (ret != 0) {
    // Print the error
    std::cout << "Unsuccessful in setting thread realtime prio" << std::endl;
  } else{
    std::cout << "Successful in setting thread realtime prio" << std::endl;
  }

  /* processor thread configuration end **/


  cout << "\nmain() : creating remoteThread " << endl;
  remoteFlag = pthread_create(&remoteThread, NULL, Remote, (void *)0);


  cout << "\nmain() : creating processorThread " << endl;
  processor = pthread_create(&processorThread, &thread_attr, ProcessorMajorTask, (void *)1);

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

  pthread_exit(NULL);
}

void zeroCrossDetect(void) {    
  zeroCross = true; // set the boolean to true to tell our dimming function that a zero cross has occured
  stepCounter=0;
  digitalWrite(FAN_PIN, PIN_LOW); // // turn off fan
}    

// Turn on the TRIAC at the appropriate time
void dimCheck(void) {                   
  if(zeroCross == true) {              
    if(stepCounter>=dim) {                     
      digitalWrite(FAN_PIN, PIN_HIGH);  // turn on fan       
      stepCounter = 0;  // reset time step counter                         
      zeroCross=false;    // reset zero cross detection
    } else {
      stepCounter++;  // increment time step counter                     
    }                                
  }    
}  

// g++ -o a.out concurrentCmSCHED_RR.cpp && ./a.out
//g++ -o a.out concurrentCmSCHED_RR.cpp -lpthread && ./a.out