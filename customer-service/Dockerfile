FROM node:20.16.0-alpine3.20 as development

WORKDIR /usr/src/app

COPY package.json yarn.lock ./

RUN yarn install  --non-interactive

COPY . .

RUN yarn build

FROM node:20.16.0-alpine3.20 as production

ENV NODE_ENV=production

WORKDIR /usr/src/app  

COPY package.json yarn.lock ./

RUN yarn install --production

COPY . .

COPY --from=development /usr/src/app/dist ./dist

CMD ["node", "dist/main"]
