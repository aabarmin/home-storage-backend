#!/bin/bash

# This script builds and deploys the application

app_build() {
    CURRENT_DIR=$(pwd)
    cd ../../backend
    chmod +x ./mvnw
    ./mvnw clean package
    cd ${CURRENT_DIR}
}

app_get_artifact_path() {
    CURRENT_DIR=$(pwd)
    cd ../../backend/backend-app/target
    TARGET=$(pwd)
    cd ${CURRENT_DIR}
    ARTIFACT_NAME=$(app_find_target_artifact_name)
    echo "${TARGET}/${ARTIFACT_NAME}"
}

app_find_target_artifact_name() {
    CURRENT_DIR=$(pwd)
    cd ../../backend/backend-app/target
    ARTIFACT_NAME=$(ls | grep '.jar$')
    echo ${ARTIFACT_NAME}
    cd ${CURRENT_DIR}
}

app_get_bucket_name() {
    BUCKET_NAME_PARAMETER="/prod/home-storage/releases/name"
    BUCKET_NAME=$(aws ssm get-parameter --name ${BUCKET_NAME_PARAMETER} | jq -r '.Parameter.Value')
    echo ${BUCKET_NAME}
}

# Parameters: 
# $1 - path to artifact to upload
# $2 - final name of the artifact
# $4 - bucket name to upload to
app_upload_artifact() {
    # Check if the source file exists
    SOURCE_PATH=$1
    if [[ -f "${SOURCE_PATH}" ]]; then
        echo "File with path ${SOURCE_PATH} exists"
    else
        echo "No file with path ${SOURCE_PATH}"
        exit
    fi

    # Check if other values are not empty
    TARGET_NAME=$2
    if [[ -z "${TARGET_NAME}" ]]; then
        echo "Target name is empty, exiting"
        exit
    fi

    TARGET_BUCKET=$3
    if [[ -z "${TARGET_BUCKET}" ]]; then
        echo "Bucket name is not provided, exiting"
        exit
    fi

    # Copy file to bucket
    TARGET_PATH="s3://${TARGET_BUCKET}/${TARGET_NAME}"
    aws s3 cp ${SOURCE_PATH} ${TARGET_PATH}
}

# Mark the given file as the latest version
# $1 - file version
# $2 - bucket name
app_mark_latest() {
    SOURCE_NAME=$1
    if [[ -z "${SOURCE_NAME}" ]]; then
        echo "Source name is empty, exiting"
        exit
    fi

    TARGET_BUCKET=$2
    if [[ -z "${TARGET_BUCKET}" ]]; then
        echo "Bucket name is not provided, exiting"
        exit
    fi

    SOURCE_PATH="s3://${TARGET_BUCKET}/${SOURCE_NAME}"
    TARGET_PATH="s3://${TARGET_BUCKET}/backend-latest.jar"
    aws s3 cp ${SOURCE_PATH} ${TARGET_PATH}
}

ec2_get_remote_public_ip() {
    EC2_INSTANCE_ID_PARAMETER="/prod/home-storage/ec2/instance-id"
    EC2_INSTANCE_ID=$(aws ssm get-parameter --name ${EC2_INSTANCE_ID_PARAMETER} | jq -r '.Parameter.Value')

    PUBLIC_IP=$(aws ec2 describe-instances --instance-ids ${EC2_INSTANCE_ID} | jq -r '.Reservations[0].Instances[0].NetworkInterfaces[0].Association.PublicIp')
    echo ${PUBLIC_IP}
}

# Build the project
echo "Building the application"
app_build
echo "Done"

# Upload the artifact
# 1. Upload with the version number
echo "Uploading the artifact"
ARTIFACT_PATH=$(app_get_artifact_path)
ARTIFACT_NAME=$(app_find_target_artifact_name)
BUCKET_NAME=$(app_get_bucket_name)
app_upload_artifact ${ARTIFACT_PATH} ${ARTIFACT_NAME} ${BUCKET_NAME}
echo "Done"

# 2. Upload with the version number
echo "Setting up the latest version"
app_mark_latest ${ARTIFACT_NAME} ${BUCKET_NAME}
echo "Done"

# Login to the server and stop the application
echo "Stopping the running version"
REMOTE_IP=$(ec2_get_remote_public_ip)
ssh -i ${AWS_KP_PATH} ec2-user@${REMOTE_IP} < ./aws_ec2_deploy_execute.sh