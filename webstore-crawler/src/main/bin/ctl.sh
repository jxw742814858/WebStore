#!/bin/bash
#
JAVA=java
JAVA_OPTS="-Xms512m -Xmx1536m -XX:PermSize=64M -XX:MaxPermSize=256m -agentlib:jdwp=transport=dt_socket,server=y,suspend=n -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
JARFILE=webstore-crawler-1.0-SNAPSHOT.jar
# resovle links - $0 may be a softlink
PRG="$0"
while [ -h "$PRG" ]; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done

# Get standard enviroment variables
PRGDIR=`dirname "$PRG"`
[ -z "$PRG_HOME" ] && PRG_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`
# Ensure that any user defined CLASSPATH variables are not used onstartup,
# but allow them to be specified in setenv.sh, in rare case when it is neede.

JARFILE=$PRGDIR/$JARFILE

if [ -z $WORKERPIDFILE ]
then WORKERPIDFILE=$PRGDIR/worker_webstore_crawler.pid
fi
    
WORKER_DAEMON_OUT="$WORKER_LOG_DIR/worker.out"

case $1 in
    start)
        echo -n "Starting worker ... "
        if [ -f $WORKERPIDFILE ]; then
            if kill -0 `cat $WORKERPIDFILE` > /dev/null 2>&1; then
                echo $command already running as process `cat $WORKERPIDFILE`.
                exit 0
            fi
        fi
        
        nohup $JAVA "-Dworker.log.dir=${WORKER_LOG_DIR}" "-Dworker.log.file=${WORKER_LOG_FILE}" \
         $JAVA_OPTS -jar $JARFILE > /dev/null 2>&1 &
        
        if [ $? -eq 0 ]; then
            echo $!
            if  echo -n $! > "$WORKERPIDFILE"  
            then
                sleep 1
                echo STARTED
            else 
                echo FAILED TO WRITE PID
                exit 1
            fi
        else
            echo SERVER DID NOT START
            exit 1
        fi
        ;;
    stop)
        echo -n "Stopping worker ... "
        if [ ! -f "$WORKERPIDFILE" ]
        then
            echo "no worker to stop (could not find file $WORKERPIDFILE)"
        else
            echo $WORKERPIDFILE
            kill -9 $(cat "$WORKERPIDFILE")
            rm "$WORKERPIDFILE"
            echo STOPPED
        fi
        ;;
    restart)
        shift
        ./"$0" stop ${@}
        sleep 3
        ./"$0" start ${@}
        ;;
    status)
        if [ ! -f "$WORKERPIDFILE" ]
        then
            echo "no worker is running."
        else
            echo "worker is running (pid=$WORKERPIDFILE)"
        fi
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status}" >&2
esac