FROM node:17-alpine

ADD . /opt
WORKDIR /opt

RUN npm install
RUN npx prisma generate
RUN npm run build

ENTRYPOINT ./docker-entrypoint.sh