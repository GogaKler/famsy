FROM node:22 as build

WORKDIR /srv/app

COPY package*.json .

RUN npm ci

COPY . .

RUN npm run build