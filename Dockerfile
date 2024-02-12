FROM node:18-alpine
RUN node -v

RUN apk update && apk add python3
RUN apk add gcc make g++
RUN apk add openjdk11-jre

RUN apk update && apk upgrade \
   && apk add --no-cache ttf-dejavu \
   # Install windows fonts as well. Not required..
   && apk add --no-cache msttcorefonts-installer \
   && update-ms-fonts && fc-cache -f

ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk/bin
ENV LD_LIBRARY_PATH /usr/lib/jvm/java-11-openjdk/lib/server

ARG NODE_ENV=production
ENV NODE_ENV=${NODE_ENV}
ARG JAVA_HOME
ARG LD_LIBRARY_PATH
ENV JAVA_HOME ${JAVA_HOME}
ENV LD_LIBRARY_PATH ${LD_LIBRARY_PATH}

RUN java -version

WORKDIR /app

COPY package*.json ./

RUN npm i
RUN node -v
COPY . .

RUN npm run build

CMD ["npm","run", "start:dev"]
