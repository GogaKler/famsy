FROM node:lts As development

RUN apt-get update && apt-get install -y openssl

WORKDIR /usr/src/app

COPY --chown=node:node package*.json ./

RUN npm i

COPY --chown=node:node . .

RUN npm run prisma:generate

USER node

CMD [ "npm", "run", "start:dev" ]
