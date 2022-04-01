FROM node:17-alpine

ADD . /opt
WORKDIR /opt

RUN npm install
RUN npm run db:generate
RUN npm run build

VOLUME [ "/opt/db", "/opt/uploads" ]

ENTRYPOINT ./docker-entrypoint.sh