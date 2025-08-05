#!/usr/bin/env just --justfile

# maven build without tests
build:
    ./mvnw package jpackage:jpackage

# build deb package
[working-directory('target/dpkg-buildpackage-working-dir')]
deb: build pre-deb
    cp -r ../../debian .
    dpkg-buildpackage -us -uc

pre-deb:
    mkdir -p target/dpkg-buildpackage-working-dir

clean:
    ./mvnw clean
