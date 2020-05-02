#include<stdio.h>
#include<wiringPi.h>

#define led 4
#define button 17

void cleanup(){
    digitalWrite(led, 0);
}

int main(void){
    wiringPiSetupGpio();

    pinMode(led, OUTPUT);// delivery output from mpu
    pinMode(button, INPUT);//get input from i/o

    delay(1000);//delay just to be safe

    for(;;){
        delay(10);//10 millisecond delay
        if(digitalRead(button) == 1){
            digitalWrite(led,1);
        }else{
            digitalWrite(led,0);
        }
    }
    //cleanup();
}