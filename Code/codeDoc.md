>https://raspberrypi.stackexchange.com/questions/8808/zero-crossing-activated-relay
>
Now, for the main loop. I'm using a switch input for "user request" but you can use network, timer or whatever. All you need is to get the boolean value into in.



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
