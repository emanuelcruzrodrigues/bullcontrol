#!/bin/bash

docker run --rm -it \
  --name bullcontrol-api \
  -p 18081:8080 \
  -v $(pwd)/target/lib:/app/lib \
  -v $(pwd)/logs:/app/logs \
  -e BULLCONTROL_URL=http://192.168.1.54:8080/bullcontrol \
  emanuelrodrigues/bullcontrol-api:250703
