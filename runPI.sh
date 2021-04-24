#!/usr/bin/env bash
kill -9 $(lsof -t -i:8080)
kill -9 $(lsof -t -i:2017)
#g++ VoltageRegulatorFinalShape.cpp -lcurl -lpthread
#  sh runHA.sh
mvn spring-boot:run