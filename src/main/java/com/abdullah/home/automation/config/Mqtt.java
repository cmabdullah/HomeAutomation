package com.abdullah.home.automation.config;

import com.abdullah.home.automation.constants.Constant;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mqtt {

    private static final Logger log = LoggerFactory.getLogger(Mqtt.class);

    private static IMqttClient instance;

    public static IMqttClient getInstance() {
        try {
            if (instance == null) {
                log.debug("================= Mqtt is starting ========================");
                instance = new MqttClient(Constant.MQTT_SERVER_ADDRESS, Constant.MQTT_PUBLISHER_ID);

                MqttConnectOptions options = new MqttConnectOptions();
                options.setAutomaticReconnect(true);
                options.setCleanSession(true);
                options.setConnectionTimeout(10);

                if (!instance.isConnected()) {
                    instance.connect(options);
                }
                log.debug("================= Mqtt config success ========================");
                return instance;
            }
        } catch (MqttException e) {
            log.error("mqtt config failed");
            log.error("================= Mqtt config failed ========================"+e.getLocalizedMessage());
            //e.printStackTrace();
        }

        return instance;
    }

    private Mqtt() {

    }
}
