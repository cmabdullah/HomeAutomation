

## 1 Channel Light Fan Dimmer Module
> https://www.techshopbd.com/product-categories/modules/3427/1-channel-light-fan-dimmer-module-techshop-bangladesh
> https://blog.techshopbd.com/2-channel-light-fan-dimmer-module-%e0%a6%95%e0%a6%bf%e0%a6%ad%e0%a6%be%e0%a6%ac%e0%a7%87-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%ac%e0%a6%b9%e0%a6%be%e0%a6%b0-%e0%a6%95%e0%a6%b0%e0%a6%ac%e0%a7%8b/
>
```ino
#include <TimerOne.h>  // Avaiable from http://www.arduino.cc/playground/Code/Timer1
volatile int i=0;               // Variable to use as a counter
volatile boolean zero_cross=0;  // Boolean to store a "switch" to tell us if we have crossed zero
int FAN = 3;   // Output to Opto Triac
int LIGHT1 = 7; // Relay-1
int LIGHT2 = 8; // Relay-2
int dim = 128;  // Dimming level (0-128)  0 = on, 128 = 0ff
int pas = 14;   // step for count;
int freqStep = 75;   // This is the delay-per-brightness step in microseconds.
char BTData; // incoming data from serial Bluetooth)

void setup() {  // Begin setup
  Serial.begin(115200); // initialization
  pinMode(FAN, OUTPUT);  // Set the Triac pin as output
  pinMode(LIGHT1, OUTPUT); // Set the Relay pin as output
  pinMode(LIGHT2, OUTPUT); // Set the Relay pin as output
                     
  attachInterrupt(0, zero_cross_detect, RISING);    // Attach an Interupt to Pin 2 (interupt 0) for Zero Cross Detection
  Timer1.initialize(freqStep);                      // Initialize TimerOne library for the freq we need
  Timer1.attachInterrupt(dim_check, freqStep);      
  // Use the TimerOne Library to attach an interrupt
}

void zero_cross_detect() {    
  zero_cross = true;     // set the boolean to true to tell our dimming function that a zero cross has occured
  i=0;
  digitalWrite(FAN, LOW);
}                                 

// Turn on the TRIAC at the appropriate time
void dim_check() {                   
  if(zero_cross == true) {              
    if(i>=dim) {                     
      digitalWrite(FAN, HIGH);  // turn on light       
      i=0;  // reset time step counter                         
      zero_cross=false;    // reset zero cross detection
    } 
    else {
      i++;  // increment time step counter                     
    }                                
  }    
}                                      

void Wireless()
{
    BTData = Serial.read(); // read byte
    if(BTData == 'a') {if(dim<127){dim = dim + pas; if(dim>127) {dim=128;}}} // Step DOWN
    if(BTData == 'A') {if(dim>5){dim = dim - pas;   if(dim<0)   {dim=0;}}}   // Step UP
    if(BTData == 'B') {dim=0;}   // power is 100% (on)
    if(BTData == 'b') {dim=128;} // power is 0% (off)
    if(BTData == 'C') {digitalWrite(LIGHT1, HIGH); } // LIGHT1 ON
    if(BTData == 'c') {digitalWrite(LIGHT1, LOW); }  // LIGHT1 OFF
    if(BTData == 'D') {digitalWrite(LIGHT2, HIGH); } // LIGHT2 ON
    if(BTData == 'd') {digitalWrite(LIGHT2, LOW); }  // LIGHT2 OFF
}

void loop() {  
                                     
delay (100);
 Wireless();
}
```

> https://gist.github.com/jmas/e817ddd9c75b1789ea61cca3a82bd8b5

### Arduino 230v Light bulb dimming

```cpp
//source: http://electronics.stackexchange.com/q/59615

int AC_LOAD = 3;    // Output to Opto Triac pin
int dimming = 128;  // Dimming level (0-128)  0 = ON, 128 = OFF

void setup()
{
  pinMode(AC_LOAD, OUTPUT);       // Set the AC Load as output
  attachInterrupt(0, zero_crosss_int, RISING);  // Choose the zero cross interrupt # from the table above
}

void zero_crosss_int()  // function to be fired at the zero crossing to dim the light
{
  // Firing angle calculation :: 50Hz-> 10ms (1/2 Cycle)
  // (10000us - 10us) / 128 = 75 (Approx)
  int dimtime = (75*dimming);      
  delayMicroseconds(dimtime);    // Off cycle
  digitalWrite(AC_LOAD, HIGH);   // triac firing
  delayMicroseconds(10);         // triac On propogation delay
  digitalWrite(AC_LOAD, LOW);    // triac Off
}

void loop()
{
  dimming = 128; 
  delay(100);
  dimming = 75;  
  delay(100);
  dimming = 25;  
  delay(100);
}
```

### controlled light dimmer

```cpp
//source: http://www.instructables.com/id/Arduino-controlled-light-dimmer-The-circuit/?lang=pt&ALLSTEPS
//Arduino controlled light dimmer: The software III
//The code below has been confirmed to work on the Leonardo

/*
AC Light Control
 
 Updated by Robert Twomey 
 
 Changed zero-crossing detection to look for RISING edge rather
 than falling.  (originally it was only chopping the negative half
 of the AC wave form). 
 
 Also changed the dim_check() to turn on the Triac, leaving it on 
 until the zero_cross_detect() turn's it off.
 
 Adapted from sketch by Ryan McLaughlin 
 <a href="http://www.arduino.cc/cgi-bin/yabb2/YaBB.pl?num=1230333861/30" rel="nofollow"> <a rel="nofollow"> http://www.arduino.cc/cgi-bin/yabb2/YaBB.pl?num=1...</a>>
(now here: <a rel="nofollow"> http://www.arduino.cc/cgi-bin/yabb2/YaBB.pl?num=1...</a>
 
 */

#include  <TimerOne.h>          // Avaiable from <a href="http://www.arduino.cc/playground/Code/Timer1" rel="nofollow"> <a href="http://www.arduino.cc/playground/Code/Timer1" rel="nofollow"> http://www.arduino.cc/cgi-bin/yabb2/YaBB.pl?num=1...</a>
</a>
volatile int i=0;               // Variable to use as a counter
volatile boolean zero_cross=0;  // Boolean to store a "switch" to tell us if we have crossed zero
int AC_pin = 11;                // Output to Opto Triac
int dim = 0;                    // Dimming level (0-128)  0 = on, 128 = 0ff
int inc=1;                      // counting up or down, 1=up, -1=down

int freqStep = 75;    // This is the delay-per-brightness step in microseconds.
                      // For 60 Hz it should be 65
// It is calculated based on the frequency of your voltage supply (50Hz or 60Hz)
// and the number of brightness steps you want. 
// 
// Realize that there are 2 zerocrossing per cycle. This means
// zero crossing happens at 120Hz for a 60Hz supply or 100Hz for a 50Hz supply. 

// To calculate freqStep divide the length of one full half-wave of the power
// cycle (in microseconds) by the number of brightness steps. 
//
// (120 Hz=8333uS) / 128 brightness steps = 65 uS / brightness step
// (100Hz=10000uS) / 128 steps = 75uS/step

void setup() {                                      // Begin setup
  pinMode(AC_pin, OUTPUT);                          // Set the Triac pin as output
  attachInterrupt(0, zero_cross_detect, RISING);    // Attach an Interupt to Pin 2 (interupt 0) for Zero Cross Detection
  Timer1.initialize(freqStep);                      // Initialize TimerOne library for the freq we need
  Timer1.attachInterrupt(dim_check, freqStep);      
  // Use the TimerOne Library to attach an interrupt
  // to the function we use to check to see if it is 
  // the right time to fire the triac.  This function 
  // will now run every freqStep in microseconds.                                            
}

void zero_cross_detect() {    
  zero_cross = true;               // set the boolean to true to tell our dimming function that a zero cross has occured
  i=0;
  digitalWrite(AC_pin, LOW);       // turn off TRIAC (and AC)
}                                 

// Turn on the TRIAC at the appropriate time
void dim_check() {                   
  if(zero_cross == true) {              
    if(i>=dim) {                     
      digitalWrite(AC_pin, HIGH); // turn on light       
      i=0;  // reset time step counter                         
      zero_cross = false; //reset zero cross detection
    } 
    else {
      i++; // increment time step counter                     
    }                                
  }                                  
}                                   

void loop() {                        
  dim+=inc;
  if((dim>=128) || (dim<=0))
    inc*=-1;
  delay(18);
}
```

### Dimmer-Arduino

```cpp
//source: https://arduinodiy.wordpress.com/2012/10/19/dimmer-arduino/

/*
AC Voltage dimmer with Zero cross detection
Author: Charith Fernanado http://www.inmojo.com
License: Creative Commons Attribution Share-Alike 3.0 License. 

Attach the Zero cross pin of the module to Arduino External Interrupt pin
Select the correct Interrupt # from the below table:
(the Pin numbers are digital pins, NOT physical pins:
digital pin 2 [INT0]=physical pin 4 
and digital pin 3 [INT1]= physical pin 5)

 Pin    |  Interrrupt # | Arduino Platform
 ---------------------------------------
 2      |  0            |  All
 3      |  1            |  All
 18     |  5            |  Arduino Mega Only
 19     |  4            |  Arduino Mega Only
 20     |  3            |  Arduino Mega Only
 21     |  2            |  Arduino Mega Only

In the program pin 2 is chosen
 */

int AC_LOAD = 3;    // Output to Opto Triac pin
int dimming = 128;  // Dimming level (0-128)  0 = ON, 128 = OFF
/* Due to timing problems, the use of ‘0’ can sometimes make the circuit 
flicker. It is safer to use a value slightly higher than ‘0’
*/
void setup()
{
  pinMode(AC_LOAD, OUTPUT);// Set AC Load pin as output
  attachInterrupt(0, zero_crosss_int, RISING);  
// Chooses '0' as interrupt for the zero-crossing
}
// the interrupt function must take no parameters and return nothing
void zero_crosss_int()  
// function to be fired at the zero crossing to dim the light
{
  // Firing angle calculation : 1 full 50Hz wave =1/50=20ms  
  // Every zerocrossing thus: (50Hz)-> 10ms (1/2 Cycle) For 60Hz => 8.33ms

  // 10ms=10000us
  // (10000us - 10us) / 128 = 75 (Approx) For 60Hz =>65
  int dimtime = (75*dimming);    // For 60Hz =>65     
  delayMicroseconds(dimtime);    // Off cycle
  digitalWrite(AC_LOAD, HIGH);   // triac firing
  delayMicroseconds(10);         // triac On propogation delay
                                 //(for 60Hz use 8.33)
  digitalWrite(AC_LOAD, LOW);    // triac Off
}

void loop() {
  for (int i=5; i <= 128; i++) {
    dimming=i;
    delay(10);
  }
}
```
### set level using up and down buttons
```cpp
//source: http://www.instructables.com/id/Arduino-controlled-light-dimmer-The-circuit/?lang=pt&ALLSTEPS
//Below a code to set the light level with up and down buttons.
//It uses a timer that checks for the time necessary to trigger the TRIAC, 
//rather than wait in a delay loop.

/*
AC Light Control
Uses up and down buttons to set levels
makes use of a timer interrupt to set the level of dimming
*/
#include <TimerOne.h>           // Avaiable from http://www.arduino.cc/playground/Code/Timer1

volatile int i=0;               // Variable to use as a counter of dimming steps. It is volatile since it is passed between interrupts
volatile boolean zero_cross=0;  // Flag to indicate we have crossed zero
int AC_pin = 3;                 // Output to Opto Triac
int buton1 = 4;                 // first button at pin 4
int buton2 = 5;                 // second button at pin 5
int dim2 = 0;                   // led control
int dim = 128;                  // Dimming level (0-128)  0 = on, 128 = 0ff
int pas = 8;                    // step for count;
int freqStep = 75;              // This is the delay-per-brightness step in microseconds. It allows for 128 steps
                                // If using 60 Hz grid frequency set this to 65

 
void setup() {  // Begin setup
  Serial.begin(9600);   
  pinMode(buton1, INPUT);  // set buton1 pin as input
  pinMode(buton2, INPUT);  // set buton1 pin as input
  pinMode(AC_pin, OUTPUT);                          // Set the Triac pin as output
  attachInterrupt(0, zero_cross_detect, RISING);    // Attach an Interupt to Pin 2 (interupt 0) for Zero Cross Detection
  Timer1.initialize(freqStep);                      // Initialize TimerOne library for the freq we need
  Timer1.attachInterrupt(dim_check, freqStep);      // Go to dim_check procedure every 75 uS (50Hz)  or 65 uS (60Hz)
  // Use the TimerOne Library to attach an interrupt

}

void zero_cross_detect() {    
  zero_cross = true;               // set flag for dim_check function that a zero cross has occured
  i=0;                             // stepcounter to 0.... as we start a new cycle
  digitalWrite(AC_pin, LOW);
}                                 

// Turn on the TRIAC at the appropriate time
// We arrive here every 75 (65) uS
// First check if a flag has been set
// Then check if the counter 'i' has reached the dimming level
// if so.... switch on the TRIAC and reset the counter
void dim_check() {                   
  if(zero_cross == true) {              
    if(i>=dim) {                     
      digitalWrite(AC_pin, HIGH);  // turn on light       
      i=0;  // reset time step counter                         
      zero_cross=false;    // reset zero cross detection flag
    } 
    else {
      i++;  // increment time step counter                     
    }                                
  }    
}                                      

void loop() {  
  digitalWrite(buton1, HIGH);
  digitalWrite(buton2, HIGH);
  
 if (digitalRead(buton1) == LOW)   
   {
  if (dim<127)  
  {
    dim = dim + pas;
    if (dim>127) 
    {
      dim=128;
    }
  }
   }
  if (digitalRead(buton2) == LOW)   
   {
  if (dim>5)  
  {
     dim = dim - pas;
  if (dim<0) 
    {
      dim=0;
    }
   }
   }
    while (digitalRead(buton1) == LOW) {  }              
    delay(10); // waiting little bit...  
    while (digitalRead(buton2) == LOW) {  }              
    delay(10); // waiting little bit...    
           

  dim2 = 255-2*dim;
  if (dim2<0)
  {
    dim2 = 0;
  }

  Serial.print("dim=");
  Serial.print(dim);
  Serial.print("     dim2=");
  Serial.print(dim2);
  Serial.print("     dim1=");
  Serial.print(2*dim);
  Serial.print('\n');
  delay (100);
}
```


> https://arduinodiy.wordpress.com/category/dimmer/

### Dimmer-Arduino code 1
```cpp
/*
AC Voltage dimmer with Zero cross detection
Author: Charith Fernanado http://www.inmojo.com
License: Creative Commons Attribution Share-Alike 3.0 License. 

Attach the Zero cross pin of the module to Arduino External Interrupt pin
Select the correct Interrupt # from the below table:
(the Pin numbers are digital pins, NOT physical pins:
digital pin 2 [INT0]=physical pin 4 
and digital pin 3 [INT1]= physical pin 5)

 Pin    |  Interrrupt # | Arduino Platform
 ---------------------------------------
 2      |  0            |  All
 3      |  1            |  All
 18     |  5            |  Arduino Mega Only
 19     |  4            |  Arduino Mega Only
 20     |  3            |  Arduino Mega Only
 21     |  2            |  Arduino Mega Only

In the program pin 2 is chosen
 */

int AC_LOAD = 3;    // Output to Opto Triac pin
int dimming = 128;  // Dimming level (0-128)  0 = ON, 128 = OFF
/* Due to timing problems, the use of ‘0’ can sometimes make the circuit 
flicker. It is safer to use a value slightly higher than ‘0’
*/
void setup()
{
  pinMode(AC_LOAD, OUTPUT);// Set AC Load pin as output
  attachInterrupt(0, zero_crosss_int, RISING);  
// Chooses '0' as interrupt for the zero-crossing
}
// the interrupt function must take no parameters and return nothing
void zero_crosss_int()  
// function to be fired at the zero crossing to dim the light
{
  // Firing angle calculation : 1 full 50Hz wave =1/50=20ms  
  // Every zerocrossing thus: (50Hz)-> 10ms (1/2 Cycle) For 60Hz => 8.33ms

  // 10ms=10000us
  // (10000us - 10us) / 128 = 75 (Approx) For 60Hz =>65
  int dimtime = (75*dimming);    // For 60Hz =>65     
  delayMicroseconds(dimtime);    // Off cycle
  digitalWrite(AC_LOAD, HIGH);   // triac firing
  delayMicroseconds(10);         // triac On propogation delay
                                 //(for 60Hz use 8.33)
  digitalWrite(AC_LOAD, LOW);    // triac Off
}
void loop()  {
 for (int i=5; i <= 128; i++)
{
 dimming=i;
 delay(10);
 }
 }
```

### Dimmer-Arduino code 2

```cpp
/*AC Light Control
 
 Updated by Robert Twomey 
 
 Changed zero-crossing detection to look for RISING edge rather
 than falling.  (originally it was only chopping the negative half
 of the AC wave form). 
 
 Also changed the dim_check() to turn on the Triac, leaving it on 
 until the zero_cross_detect() turn's it off.
 
 Adapted from sketch by Ryan McLaughlin 
 http://www.arduino.cc/cgi-bin/yabb2/YaBB.pl?num=1230333861/30
 
 */

#include  <TimerOne.h>          // Avaiable from http://www.arduino.cc/playground/Code/Timer1
volatile int i=0;               // Variable to use as a counter volatile as it is in an interrupt
volatile boolean zero_cross=0;  // Boolean to store a "switch" to tell us if we have crossed zero
int AC_pin = 11;                // Output to Opto Triac
int dim = 0;                    // Dimming level (0-128)  0 = on, 128 = 0ff
int inc=1;                      // counting up or down, 1=up, -1=down

int freqStep = 75;    // This is the delay-per-brightness step in microseconds.
                      // For 60 Hz it should be 65
// It is calculated based on the frequency of your voltage supply (50Hz or 60Hz)
// and the number of brightness steps you want. 
// 
// Realize that there are 2 zerocrossing per cycle. This means
// zero crossing happens at 120Hz for a 60Hz supply or 100Hz for a 50Hz supply. 

// To calculate freqStep divide the length of one full half-wave of the power
// cycle (in microseconds) by the number of brightness steps. 
//
// (120 Hz=8333uS) / 128 brightness steps = 65 uS / brightness step
// (100Hz=10000uS) / 128 steps = 75uS/step

void setup() {                                      // Begin setup
  pinMode(AC_pin, OUTPUT);                          // Set the Triac pin as output
  attachInterrupt(0, zero_cross_detect, RISING);    // Attach an Interupt to Pin 2 (interupt 0) for Zero Cross Detection
  Timer1.initialize(freqStep);                      // Initialize TimerOne library for the freq we need
  Timer1.attachInterrupt(dim_check, freqStep);      
  // Use the TimerOne Library to attach an interrupt
  // to the function we use to check to see if it is 
  // the right time to fire the triac.  This function 
  // will now run every freqStep in microseconds.                                            
}

void zero_cross_detect() {    
  zero_cross = true;               // set the boolean to true to tell our dimming function that a zero cross has occured
  i=0;
  digitalWrite(AC_pin, LOW);       // turn off TRIAC (and AC)
}                                 

// Turn on the TRIAC at the appropriate time
void dim_check() {                   
  if(zero_cross == true) {              
    if(i>=dim) {                     
      digitalWrite(AC_pin, HIGH); // turn on light       
      i=0;  // reset time step counter                         
      zero_cross = false; //reset zero cross detection
    } 
    else {
      i++; // increment time step counter                     
    }                                
  }                                  
}                                   

void loop() {                        
  dim+=inc;
  if((dim>=128) || (dim<=0))
    inc*=-1;
  delay(18);
}
```



------

# he tried to convert arduino code to python
> https://forum.pycom.io/topic/2659/ac-dimming-with-zero-crossing

I have a Wipy 2.0 and want to use it to dim an AC bulb with zero detection. I have the hardware I am having issues in the code. I tried to replicate an Arduino code. Both codes are below. The hardware works fine with an Arduino.
The Wipy code is running perfectly fine but the bulb is not actually turning on or dimming.

Arduino Code :

```cpp
#include <TimerOne.h>           // Avaiable from
http://www.arduino.cc/playground/Code/Timer1
//#include <Ticker.h> // For ESP8266

const int ampSense = A0;
int mVperAmp = 100; // use 100 for 20A Module and 66 for 30A Module
int RawValue = 0;
int ACSoffset = 2500;
double Voltage = 0;
double Amps = 0;

volatile int i = 0;             // Variable to use as a counter
volatile boolean zero_cross = 0; // Boolean to store a "switch" to tell us if we have crossed zero
int AC_pin = 11;                // Output to Opto Triac
int dim = 0;                    // Dimming level (0-128)  0 = on, 128 = 0ff
int inc = 1;                    // counting up or down, 1=up, -1=down

int freqStep = 78;    // This is the delay-per-brightness step in microseconds.
// It is calculated based on the frequency of your voltage supply (50Hz or 60Hz)
// and the number of brightness steps you want.
//
// The only tricky part is that the chopper circuit chops the AC wave twice per
// cycle, once on the positive half and once at the negative half. This meeans
// the chopping happens at 120Hz for a 60Hz supply or 100Hz for a 50Hz supply.

// To calculate freqStep you divide the length of one full half-wave of the power
// cycle (in microseconds) by the number of brightness steps.
//
// (1000000 uS / 120 Hz) / 128 brightness steps = 65 uS / brightness step
//
// 1000000 us / 120 Hz = 8333 uS, length of one half-wave.

//Ticker flipper; // ESP8266

void setup() {                                      // Begin setup
  pinMode(AC_pin, OUTPUT);                          // Set the Triac pin as output
  attachInterrupt(0, zero_cross_detect, RISING);   // Attach an Interupt to Pin 2 (interupt 0) for Zero Cross Detection
  Timer1.initialize(freqStep);                      // Initialize TimerOne library for the freq we need
  Timer1.attachInterrupt(dim_check, freqStep);
  // Use the TimerOne Library to attach an interrupt
  // to the function we use to check to see if it is
  // the right time to fire the triac.  This function
  // will now run every freqStep in microseconds.
  // flipper.attach_ms(freqStep, dim_check); // For ESP8266

  Serial.begin(9600);
}

void zero_cross_detect() {
  zero_cross = true;               // set the boolean to true to tell our dimming function that a zero cross has occured
  i = 0;
  digitalWrite(AC_pin, LOW);       // turn off TRIAC (and AC)
}

// Turn on the TRIAC at the appropriate time
void dim_check() {
  if (zero_cross == true) {
    if (i >= dim) {
      digitalWrite(AC_pin, HIGH); // turn on light
      i = 0; // reset time step counter
      zero_cross = false; //reset zero cross detection
    }
    else {
      i++; // increment time step counter
    }
  }
}
```

Wipy 2.0 Code :

```python
from machine import Pin
from machine import Timer
from machine import ADC
import time

#zero cross detect pin
detect = Pin('P22', mode=Pin.IN, pull=Pin.PULL_UP)

acPin = Pin('P12', mode=Pin.OUT, pull=None)

adc = ADC(0)
ampSense = adc.channel(pin='P13')

# Variables
mVperAmp = 100
rawValue = 0
acsOffset = 2500
voltage = 0
amps = 0
i = 0
zeroCross = False
dim = 0
inc = 1
freqStep = 78

def zero_cross_detect(self):
    #10,000 microseconds for half cycle and 6250 counts for half cycle
    zeroCross = True
    i = 0
    acPin.value(0)
    

class Clock:

    def __init__(self):
        self.__alarm = Timer.Alarm(self._seconds_handler, us=freqStep, periodic=True)

    def _seconds_handler(self, alarm):
		global zeroCross, i, dim, acPin
		if zeroCross == True :
			if i >= dim :
				acPin.value(1)
    			i = 0
    			zeroCross = False
    		else :
    			i += 1
clock = Clock()
detect.callback(Pin.IRQ_RISING , zero_cross_detect)



while (1) :
	dim += inc
	if dim >= 128 or dim<=0 :
		inc *= -1
	
	rawvalue = ampSense.value()
	voltage = (rawValue/4095.0)*5000
	amps = ((voltage - acsOffset) / mVperAmp)
	print(str(dim) + '\t Amps = ' + str(amps))
	time.sleep(0.02)
```

* comment 

@hjkarssies The code above missed the line:
global zeroCross, i
in the function zero_cross_detect()