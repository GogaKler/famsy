FROM node:22-slim

RUN npm install -g pnpm@latest-10

WORKDIR /srv/app

COPY package.json pnpm-lock.yaml ./

RUN pnpm install --frozen-lockfile

COPY ../.. .

CMD ["pnpm", "run", "dev:server"]