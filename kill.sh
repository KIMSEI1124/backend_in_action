#!/bin/bash

pids=$(pgrep -f "redis-server")

if [[ -z $pids ]]; then
    exit 0
fi

for pid in $pids; do
    echo "Kill 🔫 $pid"
    kill -9 $pid
done

echo "🗑️ Embedded Redis Server 종료"