import paho.mqtt.client as mqtt
import time

client = mqtt.Client()
client.connect("localhost",1883,60)
while True:
    x = 10
    if x == 10:
        client.publish("weather", "IndoorDht22  Temp={0:0.1f}  Humidity={1:0.1f} PublishTime={2:0}")
    else:
        client.publish("weather", "Failed to retrieve data from humidity IndoorDht22")

client.disconnect();