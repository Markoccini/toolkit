FROM node:20 AS build

COPY package*.json ./
RUN npm install

WORKDIR /app

COPY . .

CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]
