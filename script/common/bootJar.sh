#!/bin/sh

echo "\n\n🗄️ Start Create Boot Jar"

cd ..
chmod 755 gradlew
./gradlew clean bootJar