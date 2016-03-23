#!/usr/bin/env bash

java -Djava.library.path=/usr/lib/jni -DknakenPort=/dev/ttyACM0 -DmotorPort=/dev/ttyACM1 -Dgnu.io.rxtx.SerialPorts=/dev/ttyACM0:/dev/ttyACM1:/dev/ttyACM2:/dev/ttyACM3 -jar api-standalone.jar