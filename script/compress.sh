#!/bin/sh

export DOCKERFILE_NAME="Dockerfile.compress"
export IMAGE_NAME="kimsei/springboot"
export TAG_NAME="0.0.2"
export CONTAINER_NAME="kimsei-springboot-compress"

echo "\n\n Start Compress Docker Image Test"

sh ./common/bootJar.sh
sh ./common/docker.sh
sh ./common/test.sh