#!/bin/bash

REPOSITORY=/home/ubuntu/seb43_main_033/main
cd $REPOSITORY

APP_NAME=demo
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -fl demo | grep java | awk '{print $1}')

if [ -z "$CURRENT_PID" ]; then
    echo "실행 중이 아닙니다."
else
    echo "> kill -9 $CURRENT_PID"
    kill -15 $CURRENT_PID
    echo "실행 중인 파일을 중지하였습니다."
    sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &
