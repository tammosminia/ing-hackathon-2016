#!/bin/bash
gradle completeJar

echo "password is raspberry"
scp build/libs/api-standalone.jar pi@172.16.33.217:~/prak/
