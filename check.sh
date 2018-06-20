#!/bin/sh
APP_NAME=geek-framework

tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
        echo 'App is running.'
else
        echo 'App is NOT running.'
fi