#!/usr/bin/env just --justfile

# maven build
build:
    ./mvnw package jpackage:jpackage

# build deb package
[working-directory('target/dpkg-buildpackage-working-dir')]
deb: build pre-deb
    cp -r ../../debian .
    dpkg-buildpackage -us -uc

# preparation for deb build
pre-deb:
    mkdir -p target/dpkg-buildpackage-working-dir

# clean up build artifacts
clean:
    ./mvnw clean
