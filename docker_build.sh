#!/bin/bash

./mvnw clean package

docker build -t emanuelrodrigues/bullcontrol-api:250703 .

docker image prune -f