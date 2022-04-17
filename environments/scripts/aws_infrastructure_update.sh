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
    --template-body ${TEMPLAET_BODY_PARAM} \
    --parameters \
        ParameterKey=GoogleClientId,UsePreviousValue=true \
        ParameterKey=GoogleClientSecret,UsePreviousValue=true \
        ParameterKey=GoogleAllowedUsers,UsePreviousValue=true

echo "Waiting till stack is updated"

aws cloudformation wait stack-update-complete \
    --stack-name ${STACK_NAME}