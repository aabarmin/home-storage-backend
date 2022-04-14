#!/bin/bash

# This script logins to the instance
EC2_INSTANCE_ID_PARAMETER="/prod/home-storage/ec2/instance-id"
EC2_INSTANCE_ID=$(aws ssm get-parameter --name ${EC2_INSTANCE_ID_PARAMETER} | jq -r '.Parameter.Value')

PUBLIC_IP=$(aws ec2 describe-instances --instance-ids ${EC2_INSTANCE_ID} | jq -r '.Reservations[0].Instances[0].NetworkInterfaces[0].Association.PublicIp')
ssh -i ${AWS_KP_PATH} ec2-user@${PUBLIC_IP}
