# resources

> http://wiringpi.com/reference/priority-interrupts-and-threads/
>
>https://github.com/ngs/wiringPi/blob/master/examples/isr.c
>
>
>
>>https://raspberrypi.stackexchange.com/questions/8808/zero-crossing-activated-relay
>

##### Interrupt 
>http://wiringpi.com/reference/priority-interrupts-and-threads/
>https://github.com/ngs/wiringPi/blob/master/examples/isr.c
>https://www.digikey.com/en/maker/blogs/2019/increase-your-knowledge-of-the-gpio-c-library

# Interrupt

### wiringpi official doc

With a newer kernel patched with the GPIO interrupt handling code, you can now wait for an interrupt in your program. 
This frees up the processor to do other tasks while you’re waiting for that interrupt. The GPIO can be set to interrupt on a rising, 
falling or both edges of the incoming signal.

Note: Jan 2013: The waitForInterrupt() function is deprecated – you should use the newer and easier to use wiringPiISR() function below.

> int wiringPiISR (int pin, int edgeType,  void (*function)(void)) ;

This function registers a function to received interrupts on the specified pin. 
The edgeType parameter is either **INT_EDGE_FALLING**, **INT_EDGE_RISING**, **INT_EDGE_BOTH** or **INT_EDGE_SETUP**. 
the pin elsewhere (e.g. with the gpio program), but if you specify one of the other types, 
then the pin will be exported and initialised as specified. This is accomplished via a suitable call to the gpio utility program, 
so it need to be available.

The pin number is supplied in the current mode – native wiringPi, BCM_GPIO, physical or Sys modes.

This function will work in any mode, and does not need root privileges to work.

The function will be called when the interrupt triggers. When it is triggered, it’s cleared in the dispatcher before calling your function, 
so if a subsequent interrupt fires before you finish your handler, then it won’t be missed. (However it can only track one more interrupt, 
if more than one interrupt fires while one is being handled then they will be ignored)

**_This function is run at a high priority_** (if the program is run using sudo, or as root) and **executes concurrently with the main program**. 
It has full access to all the global variables, open file handles and so on.

See the **isr.c** example program for more details on how to use this feature.

```cpp
/*
 * isr.c:
 *	Wait for Interrupt test program - ISR method
 *
 *	How to test:
 *	  Use the SoC's pull-up and pull down resistors that are avalable
 *	on input pins. So compile & run this program (via sudo), then
 *	in another terminal:
 *		gpio mode 0 up
 *		gpio mode 0 down
 *	at which point it should trigger an interrupt. Toggle the pin
 *	up/down to generate more interrupts to test.
 *
 * Copyright (c) 2013 Gordon Henderson.
 ***********************************************************************
 * This file is part of wiringPi:
 *	https://projects.drogon.net/raspberry-pi/wiringpi/
 *
 *    wiringPi is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    wiringPi is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with wiringPi.  If not, see <http://www.gnu.org/licenses/>.
 ***********************************************************************
 */

#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <stdlib.h>
#include <wiringPi.h>


// What GPIO input are we using?
//	This is a wiringPi pin number

#define	BUTTON_PIN	0

// globalCounter:
//	Global variable to count interrupts
//	Should be declared volatile to make sure the compiler doesn't cache it.

static volatile int globalCounter = 0 ;


/*
 * myInterrupt:
 *********************************************************************************
 */

void myInterrupt (void)
{
  ++globalCounter ;
}


/*
 *********************************************************************************
 * main
 *********************************************************************************
 */

int main (void)
{
  int myCounter = 0 ;

  if (wiringPiSetup () < 0)
  {
    fprintf (stderr, "Unable to setup wiringPi: %s\n", strerror (errno)) ;
    return 1 ;
  }

  if (wiringPiISR (BUTTON_PIN, INT_EDGE_FALLING, &myInterrupt) < 0)
  {
    fprintf (stderr, "Unable to setup ISR: %s\n", strerror (errno)) ;
    return 1 ;
  }


  for (;;)
  {
    printf ("Waiting ... ") ; fflush (stdout) ;

    while (myCounter == globalCounter)
      delay (100) ;

    printf (" Done. counter: %5d\n", globalCounter) ;
    myCounter = globalCounter ;
  }

  return 0 ;
}
```


### Digikey
One of the more powerful features on microcontrollers are interrupts that fire upon a specific event occurring. 
This allows for systems to respond immediately without delay, which may be useful in situations where data may be lost 
or an emergency situation has occurred, such as a fail-safe switch. The WiringPi library has interrupts built into it 
whereby a specific function can be called depending on what happens to a specified pin. The events that can be captured 
are either rising or falling edge.

> int wiringPiISR (int pin, int edgeType,  void (*function)(void)) ;

This function should be called in your configuration section and the pin, edge type, and **function need to be passed**. 
The edge type can be one of three different options:

1. INT_EDGE_FALLING – Interrupt when the input goes from 1 to 0
2. INT_EDGE_RISING – Interrupt when the input goes from 0 to 1
3. INT_EDGE_BOTH – Interrupt when the input changes

The example below shows a program that prints a message to the console when a switch connected to GPIO0 is pushed.






```cpp
#include <iostream>     // Include all needed libraries here
#include <wiringPi.h>

using namespace std;    // No need to keep using “std”

void switchInterrupt(void);   // Function prototype

int main() {
   wiringPiSetup();        // Setup the library
   pinMode(0, OUTPUT);     // Configure GPIO0 as an output
   pinMode(1, INPUT);      // Configure GPIO1 as an input

   // Cause an interrupt when switch is pressed (0V)
   wiringPiISR (1, INT_EDGE_FALLING, switchInterrupt) ;

   // Main program loop
   while(1){
      // Toggle the LED
      digitalWrite(0, !digitalRead(0));
      delay(500);
   }

   return 0;
}

// Our interrupt routine
void switchInterrupt(void) {
   cout << “Button pressed” << endl;
}
```

# Delay


VOID DELAY (UNSIGNED INT HOWLONG) AND VOID DELAYMICROSECONDS (UNSIGNED INT HOWLONG)

These two functions are used for delays and the delay() function was used in the previous. 
The delay() function takes a number and will delay for that many milliseconds. The delayMicroseconds() function does the same, 
except it delays for that many microseconds.

Now, for the main loop. I'm using a switch input for "user request" but you can use network, timer or whatever. 
All you need is to get the boolean value into in.

```cpp
#include <iostream>     // Include all needed libraries here
#include <wiringPi.h>

using namespace std;    // No need to keep using “std”

int main() {
   wiringPiSetup();        // Setup the library
   pinMode(0, OUTPUT);     // Configure GPIO0 as an output

   // Main program loop
   while(1) {
      // Toggle the LED
         digitalWrite(0, !digitalRead(0));

      // Delay for 500ms
      delay(500);
      //delayMicroseconds()
   }
   return 0;
}
```



KEYWORD
TIMER_RESET = 1
TIMER_READ = 0
BIASED_DELAY = 19


```cpp
 while(1) {
            //when idle, return a lot of CPU time back to the system. 
            //A call every 100ms is perfectly sufficient for responsive reaction.
            usleep(100000); //cm The usleep() function suspends execution of the calling thread for (at least) usec microseconds. 
                // 1000000 us -> 100 ms
            in  = bcm2835_gpio_lev(SWITCH_PIN);
            out = bcm2835_gpio_lev(TRIAC_PIN);

            if(in==out) 
                continue;   //nothing to do; wait user input, return control to system.

            //The output needs to be changed.
            //First, let's wait for zero-crossing event.
            timer(TIMER_RESET);
            zx = bcm2835_gpio_lev(ZEROXING_PIN);

            //We don't want to freeze the system if the zero-xing input is broken.
            //If we don't get the event within reasonable time, 
            // (like three half-sines of the power; ZEROXING_TIMEOUT = 70)
            // we're going to bail.
            while(timer(TIMER_READ) < ZEROXING_TIMEOUT) {
                    if(zx != bcm2835_gpio_lev(ZEROXING_PIN)) {
                            //Event detected.                  
                            timer(TIMER_RESET);
                            break;
                    }
            }
            if(timer(TIMER_READ) >= ZEROXING_TIMEOUT) 
                continue;     //Zero-crossing detection is broken, try again soon.

            //Now we are mere milliseconds after zero-crossing event arrived
            // (but it could have taken some time to arrive) so let's wait for the next one, making adjustments for the system delay.
            // This is to be worked out using an oscilloscope and trial and error.
            // In my case BIASED_DELAY = 19.

            while(timer(TIMER_READ)<BIASED_DELAY) ;

            //We can reasonably expect if we perform this right now:
            bcm2835_gpio_set_pud(TRIAC_PIN, in);
            //the signal will reach the output right on time.

            // The 100ms delay on return to start of the loop should be enough 
            // for the signals to stabilize, so no need for extra debouncing.
    }


```

 bcm2835_gpio_lev()
 
 Reads the current level on the specified pin and returns either HIGH or LOW. Works whether or not the pin is an input or an output.
 
 Parameters
 [in]	pin	GPIO number, or one of RPI_GPIO_P1_* from RPiGPIOPin.
 Returns
 the current level either HIGH or LOW
 
 bcm2835_gpio_pud()
 void bcm2835_gpio_pud	(	uint8_t 	pud	)	
 Sets the Pull-up/down register for the given pin. This is used with bcm2835_gpio_pudclk() to set the Pull-up/down resistor for the given pin. 
 However, it is usually more convenient to use bcm2835_gpio_set_pud().
 
 Parameters
 [in]	pud	The desired Pull-up/down mode. One of BCM2835_GPIO_PUD_* from bcm2835PUDControl On the RPI 4, although this 
 function and bcm2835_gpio_pudclk() are supported for backward compatibility, new code should always use bcm2835_gpio_set_pud().
 
  bcm2835_gpio_set_pud()
 void bcm2835_gpio_set_pud	(	uint8_t 	pin, uint8_t 	pud )		
 Sets the Pull-up/down mode for the specified pin. This is more convenient than clocking the mode in 
 with bcm2835_gpio_pud() and bcm2835_gpio_pudclk().
 
 Parameters
 [in]	pin	GPIO number, or one of RPI_GPIO_P1_* from RPiGPIOPin.
 [in]	pud	The desired Pull-up/down mode. One of BCM2835_GPIO_PUD_* from bcm2835PUDControl
