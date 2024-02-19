#!/bin/sh

export DOCKERFILE_NAME="Dockerfile.default"
export IMAGE_NAME="kimsei/springboot"
export TAG_NAME="0.0.1"
export CONTAINER_NAME="kimsei-springboot-default"

echo "\n\n Start Default Docker Image Test"

sh ./common/bootJar.sh
sh ./common/docker.sh
sh ./common/test.sh