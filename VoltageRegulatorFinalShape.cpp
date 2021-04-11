#include <cstdlib>
#include <pthread.h>
#include <sched.h>

#include <curl/curl.h>
#include <boost/algorithm/string.hpp>

#include <stdio.h>   // standard input and output library
#include <stdlib.h>  // this includes functions regarding memory allocation
#include <string.h>  // contains string functions
#include <errno.h>   //It defiprocessIdnes macros for reporting and retrieving error conditions through error codes
#include <time.h>    //contains various functions for manipulating date and time
#include <unistd.h>  //contains various constants
#include <sys/types.h>   //contains a number of basic derived types that should be used whenever appropriate
#include <arpa/inet.h>   // defines in_addr structure
#include <sys/socket.h>  // for socket creation
#include <netinet/in.h>  //contains constants and structures needed for internet domain addresses
#include <iostream>  // For cout
#include <ctype.h>
using namespace std;
using namespace boost;

#define NUM_THREADS 6
volatile int stepCounter = 0;

volatile int dim = 128;
bool garbageData = false;
int processId = 0;

void readDimValueFromTextFile(void);
string convertToString(char *a, int size);
void * ProcessorMajorTask(void *threadid);
void *Remote(void *threadid);

void *ProcessorMajorTask(void *threadid) {
  long tid;
  int k = 0;
  tid = (long)threadid;
  cout << "Hello Processor! Thread ID, " << tid << endl;

  for (k = 0; k < 20; k++) {
    stepCounter++;
    printf("processor thread %d stepCounter %d \n", k, stepCounter);
  }

  pthread_exit(NULL);
}

void *Remote(void *threadid) {
  long tid;
  int j = 0;
  tid = (long)threadid;
  cout << "\nHello Remot 4 ! Thread ID, " << tid << endl;

  for (j = 0; j < 20; j++) {
    stepCounter++;
    printf("Remot 4 thread %d stepCounter %d \n", j, stepCounter);

    if (j == 15) {
      printf("dim old value %d ", dim);
      readDimValueFromTextFile();
      printf("dim new value %d ", dim);
    }
  }
  pthread_exit(NULL);
}

void *SocketCall(void *threadid) {
  long tid;

  tid = (long)threadid;
  cout << "\nHello SocketCall ! Thread ID, " << tid << endl;

  time_t clock;
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

  int   connfd = 0, l = 0;
  char recvBuff[1024];

  while (1) {
    printf("\n\nHi,Iam running server.Some Client hit me\n");  // whenever a
                                                               // request from
                                                               // client came.
                                                               // It will be
                                                               // processed
                                                               // here.
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
        clock = time(NULL);

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

int main() {
  pthread_t remoteThread;
  pthread_t processorThread;
  pthread_t socketCallThread;
  int processor, remoteFlag, ret;
  pid_t pid, ppid;

  	/* get the process id */
  	if ((pid = getpid()) < 0) {

  	  perror(" unable to get pid");
  	} else {
  	processId = pid;
  	  printf(" The process id is %d \n", pid);
  	  printf("processId is %d \n", processId);
  	}

  /**start configuring processor thread with higher priority**/
  struct sched_param param;
  pthread_attr_t thread_attr;
  pthread_attr_init(&thread_attr);  // Initialise the attributes
  // SCHED_FIFO is a simple scheduling algorithm without time slicing.
  pthread_attr_setschedpolicy(&thread_attr, SCHED_FIFO);  // Set attributes to FIFO policy
  param.sched_priority = 50;
  std::cout << "Trying to set thread realtime prio = " << param.sched_priority
            << std::endl;

  ret = pthread_attr_setschedparam(&thread_attr, &param);  // Set attributes to priority 95

  if (ret != 0) {
    // Print the error
    std::cout << "Unsuccessful in setting thread realtime prio" << std::endl;
  } else {
    std::cout << "Successful in setting thread realtime prio" << std::endl;
  }

  /* processor thread configuration end **/

  // cout << "\nmain() : creating remoteThread " << endl;
  // remoteFlag = pthread_create(&remoteThread, NULL, Remote, (void *)0);
  cout << "\nmain() : creating remoteThread " << endl;
  remoteFlag = pthread_create(&socketCallThread, NULL, SocketCall, (void *)1);

  cout << "\nmain() : creating processorThread " << endl;
  processor = pthread_create(&processorThread, &thread_attr, ProcessorMajorTask,
                             (void *)2);

  if (processor != 0) {
    printf("\nprocessor thread create failed error");
  } else {
    printf("\nprocessor thread create success : ");
  }

  // Now verify the change in thread priority
  int policy = SCHED_FIFO;
  ret = pthread_getschedparam(processorThread, &policy, &param);
  if (ret != 0) {
    cout << "Couldn't retrieve real-time scheduling paramers" << endl;
  } else {
    cout << "Retrieve real-time scheduling paramers success" << endl;
  }

  cout<< "policy "<< policy << endl;
  // Check the correct policy was applied
  if (policy != SCHED_FIFO) {
    cout << "\nScheduling is NOT SCHED_FIFO!\n" << endl;
  } else {
    cout << "\nSCHED_FIFO OK\n\n" << endl;
  }

  while(1){
    //delay(1);
  }

  pthread_exit(NULL);
}

void readDimValueFromTextFile(void) {
  int num;
  FILE *fptr;

  if ((fptr = fopen("test.txt", "r")) == NULL) {  // checks if file exists
    puts("File not exists");
    exit(1);  // for exit(1) is required #include <stdlib.h>
  } else {
    fscanf(fptr, "%d", &num);
    printf("Remote parameter is:  %d\n", num);
    fclose(fptr);
    printf("dim is:  %d\n", dim);

    if (num <= 0) {
      dim = 0;
    } else if (num >= 128) {
      dim = 128;
    } else {
      dim = num;
    }

    printf("agter read from file dim new value is:  %d\n", dim);
  }
}