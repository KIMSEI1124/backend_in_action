echo "\n\n🗑 Start Delete Docker Files"

if docker inspect $CONTAINER_NAME &> /dev/null; then
    docker rm -f $CONTAINER_NAME
fi
if docker image inspect $IMAGE_NAME:$TAG_NAME &> /dev/null; then
    docker image rm -f $IMAGE_NAME:$TAG_NAME
fi

cd ..

echo "\n\n🔨 Start Build Docker Image"
docker build \
-t $IMAGE_NAME:$TAG_NAME \
-f docker/$DOCKERFILE_NAME .

echo "\n\n🚀 Start Run Docker"
docker run -d \
--name $CONTAINER_NAME \
-p 8080:8080 \
$IMAGE_NAME:$TAG_NAME