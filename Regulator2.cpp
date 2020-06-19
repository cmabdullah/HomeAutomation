#include <iostream>  // For cout
#include <stdio.h>  // standard input and output library
#include <string.h>  // contains string functions
#include <errno.h>  //It defines macros for reporting and retrieving error conditions through error codes
#include <stdlib.h>  // this includes functions regarding memory allocation
#include <wiringPi.h>
#include <sys/time.h>
#include <time.h>   //contains various functions for manipulating date and time
#include <pthread.h>
#include <cstdlib>
#include <sched.h>
#include <curl/curl.h>
#include <boost/algorithm/string.hpp>
#include <stdlib.h>  // this includes functions regarding memory allocation
#include <unistd.h>  //contains various constants
#include <sys/types.h>   //contains a number of basic derived types that should be used whenever appropriate
#include <arpa/inet.h>   // defines in_addr structure
#include <sys/socket.h>  // for socket creation
#include <netinet/in.h>  //contains constants and structures needed for internet domain addresses
#include <ctype.h>

using namespace boost;

#define FAN1 6
using namespace std;
// Use GPIO Pin 18, which is Pin 0 for wiringPi library

bool garbageData = false;
int processId = 0;
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
void *SocketCall(void *threadid);
void myInterrupt(void);
// -------------------------------------------------------------------------
// myInterrupt:  called every time an event occurs
void myInterrupt(void) {
  eventCounter++;
}

void *SocketCall(void *threadid) {
  long tid;

  tid = (long)threadid;
  cout << "\nHello SocketCall ! Thread ID, " << tid << endl;

  char dataSending[1025];  // Actually this is called packet in Network
                           // Communication, which contain data and send
                           // through.
  int clintListn = 0, clintConnt = 0;
  struct sockaddr_in ipOfServer;
  clintListn = socket(AF_INET, SOCK_STREAM, 0);  // creating socket
  memset(&ipOfServer, '0', sizeof(ipOfServer));
  memset(dataSending, '0', sizeof(dataSending));
  ipOfServer.sin_family = AF_INET;
  ipOfServer.sin_addr.s_addr = htonl(INADDR_ANY);
  ipOfServer.sin_port =
      htons(2017);  // this is the port number of running server
  bind(clintListn, (struct sockaddr *)&ipOfServer, sizeof(ipOfServer));
  listen(clintListn, 20);

  int    l = 0;
  //char recvBuff[1024];

  while (1) {
    printf("\n\nHi,Iam running server.Some Client hit me\n");  // whenever a request from client came. It will be processed here.
    clintConnt = accept(clintListn, (struct sockaddr *)NULL, NULL);

    // Read from the connection
    char buffer[100];
    auto bytesRead = read(clintConnt, buffer, 100);
    cout << "The message was: " << buffer << endl;

    string s_a = "";

    string clientProcessId = "";

    string messageState = "";

    bool ampersand = false;

    for (l = 0; l < 100; l++) {
       //printf("buffer index %d data %c\n", l,buffer[l]);
      if (isdigit(buffer[l])) {
         printf("Got an integer\n");

         if (ampersand == false){
            s_a = s_a + buffer[l];
         } else if (ampersand == true){
            clientProcessId = clientProcessId + buffer[l];
         }

      } else if (isalpha(buffer[l])) {
        garbageData = true;
         printf("Got a char\n");
         s_a = s_a+ buffer[l];
      } else if (buffer[l] == '&'){
        ampersand = true;
        printf("ampersand find\n");
      }
      else {
         //printf("other\n");
      }
    }

    if (s_a.length() > 0 && clientProcessId.length() > 0) {
      cout << "Expected string " << s_a << endl;
      std::cout << "The size of str is " << s_a.length() << " char.\n";

      int num = stoi(s_a);

      int processIdStringToInt = stoi(clientProcessId);
      printf("garbageData %d ", garbageData);

      if (processId == processIdStringToInt){
            if (!garbageData) {
              printf("socketRes %d\n", num);

              printf("dim is:  %d\n", dim);

              if (num <= 0) {
                dim = 0;
              } else if (num >= 128) {
                dim = 128;
              } else {
                dim = num;
              }

              printf("Read from socket dim new value is:  %d\n", dim);
              messageState = "write success";
            }else{
                messageState = "write failed";
            }
      }else{
        messageState = "access denied";
        printf("Access denied");
      }

    } else{
    messageState = "access denied";
       printf("Unable to write data got from network\n");
    }

    memset(buffer, 0, sizeof buffer);

    //clock = time(NULL);

    string cma = "";
    cma = messageState;

    cout <<"messageState " <<messageState << endl;
    cout <<"cm " <<cma << endl;


        const char* s = cma.c_str();

        printf("res %s", s);


//ctime(&clock)
//&cma
    snprintf(dataSending, sizeof(dataSending), "%s\n", s);  // Printing successful message
    write(clintConnt, dataSending, strlen(dataSending));

    close(clintConnt);
    sleep(1);
  }

  pthread_exit(NULL);
}

void* zeroCrossDetector(void* threadid) {


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
      //printf("Code executed in %f milliseconds.\n", elapsed);
    } else {
      //printf("%d \n", eventCounter);
      //printf("%lld \n", current_timestamp());
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
  pthread_t socketCallThread;
  int processor, zeroCor, ret, remoteFlag;


    pid_t pid;

  	/* get the process id */
  	if ((pid = getpid()) < 0) {

  	  perror(" unable to get pid");
  	} else {
  	processId = pid;
  	  printf(" The process id is %d \n", pid);
  	  printf("processId is %d \n", processId);
  	}

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
    cout << "\nmain() : creating remoteThread " << endl;
    remoteFlag = pthread_create(&socketCallThread, NULL, SocketCall, (void *)1);
cout << remoteFlag << endl;
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