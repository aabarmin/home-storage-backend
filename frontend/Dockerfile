FROM node:17-alpine as BUILD

ADD . /opt
WORKDIR /opt

RUN npm install
RUN npm run build

FROM nginx:1.21 as RUN

COPY --from=BUILD /opt/dist/frontend /usr/share/nginx/html