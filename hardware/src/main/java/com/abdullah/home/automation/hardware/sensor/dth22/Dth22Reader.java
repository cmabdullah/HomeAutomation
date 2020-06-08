package com.abdullah.home.automation.hardware.sensor.dth22;

import com.abdullah.home.automation.hardware.sensor.Sensor;
import com.abdullah.home.automation.hardware.model.WeatherParameter;

import java.util.Objects;

public class Dth22Reader implements Sensor {

    private static WeatherParameter weatherParameter = null;
    private static Dth22DataDecoder dth22DataDecoder = null;
    private static Dth22ThreadHandler dth22ThreadHandler = null;
    private static byte[] data = null;
    private static int pinNumber;

    public Dth22Reader(int pinNumber) {
        weatherParameter = new WeatherParameter(pinNumber);
        dth22DataDecoder = new Dth22DataDecoder();
        dth22ThreadHandler = new Dth22ThreadHandler();
        data = new byte[5];
        Dth22Reader.pinNumber = pinNumber;
    }

    private static class Holder {
        private static int pin;
        private static Dth22Reader instance = new Dth22Reader(pin);
//        private static Dth22Reader instance = new Dth22Reader(25);
    }

    public static Dth22Reader getInstance(int pinAddress) {
        Holder.pin = pinAddress;
        return Holder.instance;
    }

    /**
     * Make a new sensor reading.
     *
     * @throws Exception
     */
    @Override
    public WeatherParameter readWeatherData() throws Exception {

        checkLastReadDelay();

        weatherParameter.setLastRead(System.currentTimeMillis());

        data = dth22ThreadHandler.getData(pinNumber);

        boolean check = checkParity(data);

        if (check) {
            weatherParameter.setData(data);
            weatherParameter.setHumidity(dth22DataDecoder.getReadingValueFromBytes(data[0], data[1]));
            weatherParameter.setTemperature(dth22DataDecoder.getReadingValueFromBytes(data[2], data[3]));
            weatherParameter.setLastRead(System.currentTimeMillis());
        }

        return weatherParameter;
    }

    private void checkLastReadDelay() throws Exception {
        if (Objects.nonNull(weatherParameter.getLastRead())) {
            if (weatherParameter.getLastRead() > System.currentTimeMillis() - 2000) {
                throw new Exception("Last read was under 2 seconds ago. Please wait longer between reads!");
            }
        }
    }

    private boolean checkParity(byte[] data) throws ParityCheckException {
        if (!(data[4] == (data[0] + data[1] + data[2] + data[3] & 0xFF))) {
            System.out.println("ParityCheckException " + data[0] + data[1] + data[2] + data[3]);
            throw new ParityCheckException();
        } else {
            return true;
        }
    }

    private static class ParityCheckException extends Exception {
        private static final long serialVersionUID = 1L;
    }
}
