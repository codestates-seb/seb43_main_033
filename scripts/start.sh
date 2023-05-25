PROJECT_ROOT="/home/ubuntu/seb43_main_033/main"
JAR_NAME="$PROJECT_ROOT/main-gitactions-SNAPSHOT.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

echo "$TIME_NOW > $JAR_NAME 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/*.jar $JAR_NAME

# echo "$TIME_NOW > $JAR_NAME 파일 실행" >> $DEPLOY_LOG
# nohup java -jar $JAR_NAME > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_NAME)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG
