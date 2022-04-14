#!/bin/sh
# This script updates necessary infrastructure in AWS

TEMPLATE_PATH="$(pwd)/../aws/aws-template.yml"

if [[ -f "${TEMPLATE_PATH}" ]]; then
    echo "Template file exists, continue"
else
    echo "Template file is not available"
    exit
fi

TEMPLAET_BODY_PARAM="file://${TEMPLATE_PATH}"
STACK_NAME="abarmin-home-storage"

aws cloudformation update-stack \
    --stack-name ${STACK_NAME} \
    --capabilities CAPABILITY_NAMED_IAM \
    --template-body ${TEMPLAET_BODY_PARAM}

echo "Waiting till stack is updated"

aws cloudformation wait stack-update-complete \
    --stack-name ${STACK_NAME}

echo "Updating Client Secret"

USER_POOL_PARAMETER="/prod/home-storage/cognito/pool-id"
USER_POOL_ID=$(aws ssm get-parameter --name ${USER_POOL_PARAMETER} | jq -r '.Parameter.Value')

CLIENT_PARAMETER="/prod/home-storage/cognito/client-id"
CLIENT_ID=$(aws ssm get-parameter --name ${CLIENT_PARAMETER} | jq -r '.Parameter.Value')

CLIENT_SECRET=$(aws cognito-idp describe-user-pool-client --user-pool-id ${USER_POOL_ID} --client-id ${CLIENT_ID} | jq -r '.UserPoolClient.ClientSecret')

SECRET_PARAMETER="/prod/home-storage/cognito/client-secret"
aws ssm put-parameter --name ${SECRET_PARAMETER} --value ${CLIENT_SECRET} --type String --overwrite