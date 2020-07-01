# image 

# Introduction

A home automation system is a technology that control and management over appliances and devices within a home.A home automation system typically connects controlled devices to a central hub or "gateway". The user interface for control of the system uses either wall-mounted terminals, tablet or desktop computers, a mobile phone application, or a Web interface, that may also be accessible off-site through the Internet.This home automation system will control lighting,climate, voltage control systems,and appliance

## Build

### Prerequisites

#### Java

Home Automation System is a Java application which is why you need to install a Java JDK.

If you want to build the master branch you will need a Java JDK of minimum version 1.8.

Recommanded (Oracle JDK 8) [Download this: Linux ARM 32 Hard Float ABI](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html).

#### Maven

Install the build tool [Maven](https://maven.apache.org/).

You need to ensure that Maven uses the Java JDK needed for the branch you want to build.

To do so execute

1. Download the Binary tar.gz version the [maven](http://maven.apache.org/download.cgi) website.Pick the latest version. 

> wget https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz

2. Extract the archive to /opt.

> cd /opt && sudo tar -xzvf /path/to/apache-maven-3.6.3-bin.tar.gz

3. Tell you shell where to find maven. We’ll do this in the system profile settings so it is available to all users.

> sudo vim /etc/profile.d/maven.sh
```bash
	export M2_HOME=/opt/apache-maven-3.6.3
	export "PATH=$PATH:$M2_HOME/bin"
```
Quit and save from the editor.

4. Log out and back into the Raspberry Pi so the profile script takes effect and there it is. You can test that it is working with

> mvn -version


#### Git

Install the version control tool [git](https://git-scm.com/) and clone this repository with


> git clone https://github.com/cmabdullah/HomeAutomation.git


#### WiringPi

For installing WiringPi 

> sudo apt-get install wiringpi


#### Adafruit_DHT

For installing Adafruit_DHT


> sudo apt-get install python3-dev python3-pip

> sudo python3 -m pip install --upgrade pip setuptools wheel

> sudo pip3 install Adafruit_DHT

#### MySql

> sudo apt install mariadb-server

#### Message Broker

Execute the following

> sudo apt install -y mosquitto mosquitto-clients

> sudo systemctl enable mosquitto.service

#### Install Boost on RasberyPi

> sudo apt-get install libboost-dev


### Build And Run Command

After you have taken care of the [Prerequisites](#prerequisites)

Execute the following

> mvn spring-boot:runge


# Installation Problems

# FAQ

### What is Lighting control ?
You can turn on an off lights from this system.

### What is Climate Monitoring?
You can monitor temparature, humidity from your room and also observe world wide weather information.

### What is voltage control System?
You can handle fan's rotation speed.Suppose you can spped up your fan's speed you can speed up your fan's rotation speed from this system.

# License

## Developers Guide:
  + md1
  + md2
  + md2
  + Database Configuration
