#!/bin/bash
./gradlew completeJar

echo "password is raspberry"
scp build/libs/api-standalone.jar pi@10.130.136.203:~/prak/
