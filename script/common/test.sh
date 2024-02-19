echo "\n\n💯 Start Docker Image & SpringBoot Test"

until docker logs $CONTAINER_NAME | grep "Started" &> /dev/null; do
    sleep 0.5
done

ls -lh ../build/libs
docker images | grep $IMAGE_NAME
curl -s -X GET http://localhost:8080/get

echo "\n\n⛔ Docker Container"
if docker inspect $CONTAINER_NAME &> /dev/null; then
    docker rm -f $CONTAINER_NAME
fi
if docker image inspect $IMAGE_NAME:$TAG_NAME &> /dev/null; then
    docker image rm -f $IMAGE_NAME:$TAG_NAME
fi