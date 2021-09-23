FROM node:alpine

WORKDIR /usr/local/front

COPY package.json .

RUN npm install

COPY . .

CMD [ "npm", "start" ]