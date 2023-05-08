FROM node:alpine

RUN mkdir /project
WORKDIR /project

COPY . .

RUN npm install -g @angular/cli
CMD ["ng", "serve", "--host", "0.0.0.0"]

