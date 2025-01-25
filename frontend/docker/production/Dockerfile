FROM node:lts as build

WORKDIR /srv/app

COPY package*.json .

RUN npm ci

COPY . .

RUN npm run build