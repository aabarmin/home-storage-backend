#!/bin/sh

# Installing posgresql client
yum install -y postgresql.x86_64

# Installing JDK
yum install -y java-11-amazon-corretto

# Installing misc useful software
yum install -y htop
yum install -y mc