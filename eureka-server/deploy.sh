#!/bin/sh

export IMAGE_NAME="kimsei/eureka-server"
export TAG_NAME="passport"

echo "\n\n🗄️ Start Create Boot Jar"

chmod 755 gradlew
./gradlew clean bootJar

echo "\n\n🗑 Start Delete Docker Files"

if docker image inspect $IMAGE_NAME:$TAG_NAME &> /dev/null; then
    docker image rm -f $IMAGE_NAME:$TAG_NAME
fi

echo "\n\n🔨 Start Build Docker Image"
docker build \
-t $IMAGE_NAME:$TAG_NAME .