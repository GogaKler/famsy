FROM node:22 as build

WORKDIR /srv/app

COPY ../../package*.json .

RUN npm install -g pnpm@latest-10

RUN pnpm install --frozen-lockfile || pnpm install

COPY ../.. .