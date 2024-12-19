FROM node:22 As development

RUN apt-get update && apt-get install -y openssl

WORKDIR /usr/src/app

COPY --chown=node:node package*.json ./

RUN npm i

COPY --chown=node:node . .

RUN npm run prisma:generate

RUN chown -R node:node /usr/src/app

USER node

CMD [ "npm", "run", "start:dev" ]
