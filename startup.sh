#!/bin/sh
rm -f tpid
nohup java -jar target/spring-boot-web.jar --spring.profiles.active=pro > /var/log/spring-boot-web/startup`date +%Y-%m-%d`.log 2>&1 &
echo $! > tpid
echo Start Success!
