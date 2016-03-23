#!/bin/bash
gradlew completeJar

echo "password is raspberry"
scp build/libs/api-standalone.jar pi@172.16.35.56:~/prak/
