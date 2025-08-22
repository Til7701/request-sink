# syntax=docker/dockerfile:1

FROM ubuntu:25.10

WORKDIR /app

COPY ./target/jpackage-image/request-sink/ .

CMD ["./bin/request-sink"]

EXPOSE 8080
