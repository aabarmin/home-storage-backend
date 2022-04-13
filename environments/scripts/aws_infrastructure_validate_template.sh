#!/bin/sh
# This script validates the CloudFormation template

TEMPLATE_PATH="$(pwd)/../aws/aws-template.yml"

if [[ -f "${TEMPLATE_PATH}" ]]; then
    echo "Template file exists, continue"
else
    echo "Template file is not available"
    exit
fi

TEMPLAET_BODY_PARAM="file://${TEMPLATE_PATH}"

aws cloudformation validate-template \
    --template-body ${TEMPLAET_BODY_PARAM}