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

aws cloudformation update-stack \
    --stack-name abarmin-home-storage \
    --template-body ${TEMPLAET_BODY_PARAM}