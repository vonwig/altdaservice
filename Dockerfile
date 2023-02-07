FROM clojure:openjdk-11-lein-2.9.8-slim-buster@sha256:9d878363d7d96a322362fa795acd35d4cde4f8c1d831afc125aa2d2ef7769cd8 as builder

RUN mkdir /build

WORKDIR /build

COPY project.clj /build
COPY src /build/src

RUN lein metajar

FROM eclipse-temurin:11.0.18_10-jre-jammy@sha256:b1cffed1b0816ff3484fd82636142797b9bf61c01fd5a8b7dbc7f5de3ce42a80

RUN apt-get -y --allow-remove-essential purge bash
RUN apt-get -y remove openssl
RUN apt-get -y purge openssl

RUN mkdir -p /usr/src/app \
    && mkdir -p /usr/src/app/bin \
    && mkdir -p /usr/src/app/lib

WORKDIR /usr/src/app

COPY --from=builder /build/target/lib /usr/src/app/lib

COPY --from=builder /build/target/metajar/service.jar /usr/src/app/
ENV APP_NAME=service

EXPOSE 3000

# Set up labels to make image linking work
ARG COMMIT_SHA
ARG DOCKERFILE_PATH=Dockerfile

LABEL org.opencontainers.image.revision=$COMMIT_SHA \
  org.opencontainers.image.source=$DOCKERFILE_PATH

CMD ["java","-Djava.net.preferIPv4Stack=true", "-jar", "/usr/src/app/service.jar", "-Dclojure.core.async.pool-size=20"]

