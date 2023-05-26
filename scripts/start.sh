PROJECT_ROOT="/home/ubuntu/seb43_main_033"
JAR_NAME="$PROJECT_ROOT/main/main-0.0.1-SNAPSHOT.jar"

APP_LOG="$PROJECT_ROOT/main/log/application.log"
ERROR_LOG="$PROJECT_ROOT/main/log/error.log"
DEPLOY_LOG="$PROJECT_ROOT/main/log/deploy.log"

TIME_NOW=$(date +%c)

echo "SERVER START.sh 실행" >> $DEPLOY_LOG 

echo "$TIME_NOW > $JAR_NAME 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/main/build/libs/*.jar $JAR_NAME

echo "$TIME_NOW > $JAR_NAME 파일 실행" >> $DEPLOY_LOG
nohup java -jar $JAR_NAME > $APP_LOG 2> $ERROR_LOG &

sleep 3

CURRENT_PID=$(pgrep -f $JAR_NAME)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG
