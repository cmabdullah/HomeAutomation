package com.abdullah.home.automation.hardware.sensor.dth22;

import java.util.concurrent.*;

public class Dth22ThreadHandler {

    protected byte[] getData(int pinNumber) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Dth22SensorReading readSensor = new Dth22SensorReading(pinNumber);
        Future<byte[]> future = executor.submit(readSensor);

        // Reset data
        byte[] data;

        try {
            data = future.get(3, TimeUnit.SECONDS);
            readSensor.close();
        } catch (TimeoutException e) {
            System.out.println("ExecutorService call TimeoutException "+ e.getLocalizedMessage());
            e.printStackTrace();
            readSensor.close();
            future.cancel(true);
            executor.shutdown();
            throw e;
        }

        readSensor.close();
        executor.shutdown();

        return data;
    }

}
