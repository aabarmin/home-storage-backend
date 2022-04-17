#!/bin/bash

# Check if necessary directories are available
if [[ ! -d "/opt/app" ]]; then
    echo "No /opt/app directory, creating"
    cd /opt
    sudo mkdir app
    sudo chown ec2-user app
fi

# Stopping the current version
cd /opt/app
if [[ -f "app.pid" ]]; then
    PROCESS_ID=$(cat app.pid)
    echo "Killing the app with pid ${PROCESS_ID}"
    sudo kill ${PROCESS_ID}
    rm -rf app.pid
fi

# Removing the current version of the app
if [[ -f "backend-latest.jar" ]]; then
    echo "Removing existing version of the app"
    rm -f backend-latest.jar
fi

# Downloading a new version of the app
# TODO: Read name of the bucket from AWS SSM Parameter Store
ARTIFACT_NAME="s3://abarmin-home-storage-releases-2022/backend-latest.jar"
aws s3 cp ${ARTIFACT_NAME} backend-latest.jar

# Running a new version of the app
# TODO: Logs of the previous versions should be stored somewhere
nohup java -jar ./backend-latest.jar --spring.profiles.active=prod > backend-latest.log 2>&1 &
echo $! > app.pid