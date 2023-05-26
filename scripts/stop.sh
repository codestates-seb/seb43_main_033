PROJECT_ROOT="/home/ubuntu/seb43_main_033"
JAR_NAME="$PROJECT_ROOT/main/main-0.0.1-SNAPSHOT.jar"

mkdir "$PROJECT_ROOT/main/log"

DEPLOY_LOG="$PROJECT_ROOT/main/log/deploy.log"

TIME_NOW=$(date +%c)

CURRENT_PID=$(pgrep -f $JAR_NAME)

echo "SERVER STOP.sh 실행" >> $DEPLOY_LOG 

if [ -z $CURRENT_PID ]; then
    echo "$TIME_NOW > 현재 실행중인 애플리케이션이 없습니다" >> $DEPLOY_LOG
else
    echo "$TIME_NOW > 현재 실행중인 PID는 $CURRENT_PID 입니다." >> $DEPLOY_LOG
    echo "$TIME_NOW > 실행중인 $CURRENT_PID 애플리케이션 종료 시도 " >> $DEPLOY_LOG
    kill -15 $CURRENT_PID
    
    sleep 3
    
    if pgrep -f $JAR_NAME > /dev/null; then
        echo "$TIME_NOW > $CURRENT_PID 애플리케이션이 아직 종료되지 않았습니다. 5초 후에도 종료되지 않으면 강제 종료합니다." >> $DEPLOY_LOG
        sleep 5
        if pgrep -f $JAR_NAME > /dev/null; then
            echo "$TIME_NOW > $CURRENT_PID 애플리케이션 종료하지 못했습니다. 강제 종료합니다." >> $DEPLOY_LOG
            kill -9 $CURRENT_PID
            echo "$TIME_NOW > $CURRENT_PID 애플리케이션 강제 종료 완료." >> $DEPLOY_LOG
        else
            echo "$TIME_NOW > $CURRENT_PID 애플리케이션이 정상적으로 종료되었습니다." >> $DEPLOY_LOG
        fi
    else
        echo "$TIME_NOW > $CURRENT_PID 애플리케이션이 정상적으로 종료되었습니다." >> $DEPLOY_LOG
    fi
fi
