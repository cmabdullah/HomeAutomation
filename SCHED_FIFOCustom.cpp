
/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, PHP, Ruby,
C#, VB, Perl, Swift, Prolog, Javascript, Pascal, HTML, CSS, JS
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
#include <stdio.h>
#include <pthread.h>
#include <sched.h>
#include <iostream>
#include <sys/types.h>
using namespace std;

pthread_t thread1;

void *
ProcessorMajorTask (void *i) {
  struct sched_param p3;

  int i1 = 0;

  int policy = SCHED_FIFO;
cout<<"SCHED_FIFO " << SCHED_FIFO << endl;
  i1 = pthread_getschedparam (thread1, &policy, &p3);

  printf ("\n in thread1 with priority :%u policy: %u\n", p3.sched_priority,
	  policy);

	  cout<<"i1 " << i1 << endl;



  /***
  // Now verify the change in thread priority
  ret = pthread_getschedparam(thread1, (int *)&policy, &p3);
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

  **/

}

int
main ()
{


struct sched_param param;

  int pid_num = 0, ret;
  int processor;



  pthread_attr_t thread_attr;

  //modification
  pthread_attr_init (&thread_attr);	// Initialise the attributes


  pthread_attr_setschedpolicy (&thread_attr, SCHED_FIFO);
/**

  ret = pthread_attr_setschedpolicy (&thread_attr, SCHED_FIFO);

  if (ret != 0){
      // Print the error
      cout << "Unsuccessful in setting thread realtime prio" << endl;

    } else {
      cout << "Successful in setting thread realtime prio" << endl;
    }**/

  param.sched_priority = sched_get_priority_max (SCHED_FIFO);

  cout << "Trying to set thread realtime prio = " << param.sched_priority
    << endl;



//stack
  ret = pthread_attr_setschedparam (&thread_attr, &param);	// Set attributes to priority 95
  if (ret != 0)
    {
      // Print the error
      std::cout << "Unsuccessful in setting thread realtime prio" << std::
	endl;
    }
  else
    {
      std::cout << "Successful in setting thread realtime prio" << std::endl;
    }





  processor =
    pthread_create (&thread1, &thread_attr, ProcessorMajorTask, (void *) 2);


  if (processor != 0)
    {
      printf ("\nprocessor thread create failed error");
    }
  else
    {
      printf ("\nprocessor thread create success : ");
    }





  //verify

  //https://stackoverflow.com/questions/31404770/unable-to-change-thread-policy-to-sched-fifo




  while (1)
    {

    }



/***
   param.sched_priority = sched_get_priority_max (SCHED_FIFO);
   sched_setscheduler(pid_num, SCHED_FIFO, &param);
   cout << "Thread priority is " << param.sched_priority << endl;

   **/
  return 0;
}
