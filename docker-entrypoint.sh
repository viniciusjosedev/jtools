#!/bin/sh

export TERM=xterm

./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$PORT_DEBUG" &

while true; do
    watch -d -t -g "ls -lR . | sha1sum" && ./mvnw compile
done