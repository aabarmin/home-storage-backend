FROM node:17-alpine

ADD . /opt
WORKDIR /opt

RUN npm install
RUN npm run db:generate
RUN npm run build

ENV ENVIRONMENT=local
ENV UPLOAD_DIRECTORY=./uploads
ENV SERVER_PORT=8080

VOLUME [ "/opt/db", "/opt/uploads" ]

ENTRYPOINT ./docker-entrypoint.sh