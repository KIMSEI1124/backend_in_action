echo "Start Redis Server"

docker run -d \
--name redis-passport \
-p 6379:6379 \
redis

echo "Start Spring Server"

cd ..
chmod 755 gradlew
./gradlew clean bootRun