/*
 * isr.c:
 * Wait for Interrupt test program - ISR method
 to do make it parallel
 read dim value
 ***********************************************************************
 */
#include <iostream>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <stdlib.h>
#include <wiringPi.h>

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

void setDimMethod(void){
    dim = 64;
}

int main (void){

  if (wiringPiSetup () < 0) {
    fprintf (stderr, "Unable to setup wiringPi: %s\n", strerror (errno)) ;
    return 1 ;
  }

  pinMode(FAN_PIN, OUTPUT);  // Set the Triac pin as output

  if (wiringPiISR (INTERRUPT_PIN, INT_EDGE_RISING, &zeroCrossDetect) < 0) {
    fprintf (stderr, "Unable to setup ISR: %s\n", strerror (errno)) ;
    return 1 ;
  }

  setDimMethod();

  for (;;) {

    printf ("Waiting ... ") ;

    delayMicroseconds(FREQ_STEP);

    dimCheck();
    fflush (stdout) ;

    printf ("dimCheck called -> \n") ;

  }
  return 0 ;
}
