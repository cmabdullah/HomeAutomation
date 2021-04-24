import Adafruit_DHT
import paho.mqtt.client as mqtt
import time

DHT_SENSOR = Adafruit_DHT.DHT22
DHT_PIN = 26
client = mqtt.Client()
client.connect("localhost",1883,60)
while True:
    humidity, temperature = Adafruit_DHT.read_retry(DHT_SENSOR, DHT_PIN)

    if humidity is not None and temperature is not None:
        millis = int(round(time.time() * 1000))
        # print("IndoorDht22  Temp={0:0.1f}  Humidity={1:0.1f} PublishTime={2:0}".format(temperature, humidity, millis))

        client.publish("weather", '{"sensorType":"IndoorDht22",  "Temp":{0:0.1f} , "Humidity":{1:0.1f}, "PublishTime":{2:0}}'.format(temperature, humidity, millis))
    else:
        # print("Failed to retrieve data from humidity sensor indoor")
        client.publish("weather", '"IndoorDht22Error": Failed to retrieve data from humidity IndoorDht22');

client.disconnect();
