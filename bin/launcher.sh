#!/bin/sh

HERE=$(dirname $0)

cd ${HERE}
if [ $(id -u) = 0 ]
then
    chown -R pi:pi ${HERE}
    su pi -c "cd ${HERE} && java -jar chronos-*.jar"
else
    java -jar chronos-*.jar
fi
