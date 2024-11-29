FROM node:lts as build

WORKDIR /srv/app

COPY ../../package*.json .

RUN npm install

COPY ../.. .