# Request Sink

A small cli application for logging requests to see what they look like.

> [!CAUTION]
> Do not use this in a production environment. There are no protections from malicious requests.

## Usage

Just run the app and send a request to `localhost:8080`.
Using a simple `wget` call the following output is produced.
You can use `request-sink --help` to get a list of options to configure.

```
$» request-sink        
Listening for requests on port 8080
Received Request: 
Time: 2025-07-02T09:58:54.628260806Z
Method: GET
URI: /test1?foo=bar
========== Request Start ==========
---------- Header Start ----------
Accept-encoding: identity
Accept: */*
Connection: Keep-Alive
Host: localhost:8080
User-agent: Wget/1.24.5
---------- Header End ----------
---------- No body in request ----------
========== Request End ==========
```

## Installation

### Linux

#### DEB Package (Debian, Ubuntu...)

If you have `apt` or `dpkg` installed, download the latest release from GitHub and run one of the following commands
depending on what you want to use. `apt` is recommended. Depending on your setup, you might have to run them with
`sudo`.

```bash
apt install request-sink_0.0.1-SNAPSHOT_amd64.deb
```

```bash
dpkg -i request-sink_0.0.1-SNAPSHOT_amd64.deb
```

#### Other

If you cannot or do not want to install a deb package, you can modify the `pom.xml` file and build the package yourself.
To do that, modify the following line at the bottom of the file and build the package using the build instructions
below.

```
<type>DEB</type>
```

Here you can choose from the following options: `APP_IMAGE` or `RPM`.
`APP_IMAGE` is not the click and play AppImage, but an archive containing all files which you can put anywhere and link
the binary in your shell.

### Windows

Follow the build instructions below and run the `msi` file in the `target` directory.

## Build

To build the project, you need the following:

- Java 24 or newer in your PATH or JAVA_HOME
- on Linux you need to have `dpkg` and some other tools installed. JLink and JPackage will tell you if something is
  missing.
- on Windows, you need to have WiX Toolset installed (https://wixtoolset.org/)

> [!NOTE]
> On Windows use WiX 3.14 to create the installer. Newer versions might not be supported by JPackage.

Then run the following command:

```bash
./mvnw clean package jpackage:jpackage
```

If you don't need the installer, you can leave out the `jpackage:jpacakge` arg.
