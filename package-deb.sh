#!/bin/sh

set -e

VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)
JPACKAGE_APP_NAME=request-sink
DEB_PACKAGE_NAME=request-sink

cd target

echo "Creating folder structure..."
mkdir -p deb-package/${DEB_PACKAGE_NAME}
cp -rf ../deb/* deb-package/request-sink
mkdir -p deb-package/${DEB_PACKAGE_NAME}/usr/bin
mkdir -p deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib
mkdir -p deb-package/${DEB_PACKAGE_NAME}/usr/share/man/man1

echo "Copying files..."
# from jpackage-image
cp -rf jpackage-image/${JPACKAGE_APP_NAME}/bin/* deb-package/${DEB_PACKAGE_NAME}/usr/bin
cp -rf jpackage-image/${JPACKAGE_APP_NAME}/lib/* deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib
# man page
gzip -9 -cn generated-docs/request-sink.1 > deb-package/${DEB_PACKAGE_NAME}//usr/share/man/man1/request-sink.1.gz

echo "Stripping binaries..."
strip --strip-unneeded --remove-section=.comment --remove-section=.note \
deb-package/${DEB_PACKAGE_NAME}/usr/bin/${DEB_PACKAGE_NAME} \
deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/*.so \
deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/runtime/bin/java \
deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/runtime/bin/jwebserver \
deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/runtime/bin/keytool \
deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/runtime/lib/*.so \
deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/runtime/lib/jexec \
deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/runtime/lib/jspawnhelper \
deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/runtime/lib//server/*.so \
--verbose

echo "Setting permissions..."
find deb-package/${DEB_PACKAGE_NAME}/ -type d -exec chmod 755 {} \;
find deb-package/${DEB_PACKAGE_NAME}/ -type f -exec chmod 644 {} \;
chmod 755 -R deb-package/${DEB_PACKAGE_NAME}/usr/bin/*
chmod 755 deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/runtime/lib/jexec
chmod 755 deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/runtime/lib/jspawnhelper
chmod 755 -R deb-package/${DEB_PACKAGE_NAME}/usr/lib/${DEB_PACKAGE_NAME}/lib/runtime/bin/*
find . -name '*.so' -exec chmod 644 {} \;
chmod 775 deb-package/${DEB_PACKAGE_NAME}/DEBIAN/preinst


echo "Building DEB package for Request-Sink version ${VERSION}..."
dpkg-deb --root-owner-group --build deb-package/${DEB_PACKAGE_NAME} deb-package/${DEB_PACKAGE_NAME}_${VERSION}_amd64.deb
mv deb-package/*.deb .
echo "DEB built successfully"
