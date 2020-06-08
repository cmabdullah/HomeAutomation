package com.abdullah.home.automation.hardware.sensor.dth22;

import com.abdullah.home.automation.hardware.config.PinConfig;
import com.pi4j.wiringpi.Gpio;

import java.io.Closeable;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class Dth22SensorReading implements Callable<byte[]>, Closeable {

    private boolean keepRunning = true;

    private int pinNumber;

    public Dth22SensorReading(int pinNumber) {
        this.pinNumber = pinNumber;
        Gpio.pinMode(pinNumber, Gpio.OUTPUT);
        Gpio.digitalWrite(pinNumber, Gpio.HIGH);
    }

    @Override
    public byte[] call() {
        // do expensive (slow) stuff before we start.
        byte[] data = new byte[5];
        long startTime;

        sendStartSignal();
        waitForResponseSignal();

        for (int i = 0; i < 40; i++) {

            while (keepRunning && Gpio.digitalRead(pinNumber) == Gpio.LOW) {

            }

            startTime = System.nanoTime();

            while (keepRunning && Gpio.digitalRead(pinNumber) == Gpio.HIGH) {

            }

            long timeHight = System.nanoTime() - startTime;
            data[i / 8] <<= 1;

            if (timeHight > PinConfig.LONGEST_ZERO) {
                data[i / 8] |= 1;
            }
        }

        System.out.println("===============byte read start ===============");
        System.out.println(Arrays.toString(data));
        System.out.println("===============byte read end  ================");

        return data;
    }

    private void sendStartSignal() {
        // Send start signal.
        Gpio.pinMode(pinNumber, Gpio.OUTPUT);
        Gpio.digitalWrite(pinNumber, Gpio.LOW);
        Gpio.delay(1);
        Gpio.digitalWrite(pinNumber, Gpio.HIGH);
    }

    /**
     * AM2302 will pull low 80us as response signal, then
     * AM2302 pulls up 80us for preparation to send data.
     */
    private void waitForResponseSignal() {
        Gpio.pinMode(PinConfig.DTH_22_PIN_CONFIG.getAddress(), Gpio.INPUT);

        while (keepRunning && Gpio.digitalRead(pinNumber) == Gpio.HIGH) {

        }

        while (keepRunning && Gpio.digitalRead(pinNumber) == Gpio.LOW) {

        }

        while (keepRunning && Gpio.digitalRead(pinNumber) == Gpio.HIGH) {

        }
    }

    @Override
    public void close() {
        keepRunning = false;
        // Set pin high for end of transmission.
        Gpio.pinMode(pinNumber, Gpio.OUTPUT);
        Gpio.digitalWrite(pinNumber, Gpio.HIGH);
    }
}
