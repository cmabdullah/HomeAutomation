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