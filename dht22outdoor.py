import Adafruit_DHT
import paho.mqtt.client as mqtt

DHT_SENSOR = Adafruit_DHT.DHT22
DHT_PIN = 19
client = mqtt.Client()
client.connect("localhost",1883,60)
while True:
    humidity, temperature = Adafruit_DHT.read_retry(DHT_SENSOR, DHT_PIN)

    if humidity is not None and temperature is not None:
        print("Outdoor Temp={0:0.1f}*C  Humidity={1:0.1f}%".format(temperature, humidity))
        client.publish("weather", "Outdoor Temp={0:0.1f}*C  Humidity={1:0.1f}%".format(temperature, humidity));
    else:
        print("Failed to retrieve data from humidity sensor outdoor")
        client.publish("weather", "Failed to retrieve data from humidity sensor outdoor");

client.disconnect();