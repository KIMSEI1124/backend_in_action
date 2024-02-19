#!/bin/sh

export IMAGE_NAME="kimsei/springboot"
export TAG_NAME="0.0.1"
export CONTAINER_NAME="kimsei-springboot-default"

echo "\n\n Start Default Docker Image Test"

sh bootJar.sh
sh docker.sh