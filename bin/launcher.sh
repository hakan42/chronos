#!/bin/sh

HERE=$(dirname $0)

CONTEXT_PATH=/chronos-sf

cd ${HERE}
if [ $(id -u) = 0 ]
then
    chown -R pi:pi ${HERE}
    su pi -c "cd ${HERE} && java -Dserver.context-path=${CONTEXT_PATH} -jar chronos-*.jar"
else
    java -Dserver.context-path=${CONTEXT_PATH} -jar chronos-*.jar
fi
