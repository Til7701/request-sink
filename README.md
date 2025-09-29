# Request Sink

A small cli application for logging requests to see what they look like.

> [!CAUTION]
> Do not use this in a production environment. There are no protections from malicious requests.

## Usage

```
Usage: request-sink [-hV] [--debug] [-p=<port>] [-s=<statusCode>] [COMMAND]
A simple http server listening for requests and logging them.
      --debug         Enable debug output
  -h, --help          Show this help message and exit.
  -p, --port=<port>   The port to listen for requests on.
                        Default: 9090
  -s, --status-code=<statusCode>
                      The status code to return.
                        Default: 200
  -V, --version       Print version information and exit.
Commands:
  config  Configures default options for the request sink server
```

Just run the app and send a request to `localhost:8080`.
Using simple `curl` and `wget` calls the following output is produced.

```
$» request-sink        
Responding with status code: 200 - OK
Listening for requests on port 8080 - HTTP Alternate (see port 80)
================ Request 0 Start ================
Time:   2025-09-29T13:39:32.321047540Z
Method: POST
URI:    /
----------------- Headers Start -----------------
Accept:         */*
Host:           localhost:8080
User-agent:     curl/8.12.1
Content-type:   application/x-www-form-urlencoded
Content-length: 18
------------------ Headers End ------------------
------------------ Body Start -------------------
fancy request body
------------------- Body End --------------------
================= Request 0 End =================

========== Request 1 Start ===========
Time:   2025-09-29T13:40:59.473792817Z
Method: GET
URI:    /
----------- Headers Start ------------
Accept-encoding: identity
Accept:          */*
Connection:      Keep-Alive
Host:            localhost:8080
User-agent:      Wget/1.24.5
------------ Headers End -------------
--------- No body in request ---------
=========== Request 1 End ============
```

## Installation

### Linux

#### DEB Package (Debian, Ubuntu, ...)

##### PPA

This package is included in the [Schlunzis PPA](https://github.com/schlunzis/ppa).
Follow the instructions there to set up the ppa. Then run the following.

```bash
sudo apt update
sudo apt install request-sink
```

##### Manual Installation

Alternatively, if you have `apt` or `dpkg` installed, download the latest release from GitHub and run one of the
following commands depending on what you want to use. `apt` is recommended.

```bash
sudo apt install request-sink_0.0.4_amd64.deb
```

```bash
sudo dpkg -i request-sink_0.0.4_amd64.deb
```

##### Post Installation

After installing the deb package, see [Wiki](https://github.com/Til7701/request-sink/wiki/Post-Installation) for further
configuration.

#### Other

If you cannot or do not want to install a deb package, you can modify the `pom.xml` file and build the package yourself.
To do that, build the package using the build instructions below.

### Windows

Follow the build instructions below and run the `msi` file in the `target` directory.

## Build

To build the project, you need the following:

- Java 25 or newer in your PATH or JAVA_HOME
- on Linux, you need to have `dpkg build-essential debhelper devscripts` installed. JLink and JPackage will tell you if
  something is missing.
- on Windows, you need to have WiX Toolset installed (https://wixtoolset.org/)

> [!NOTE]
> On Windows use WiX 3.14 to create the installer. Newer versions might not be supported by JPackage.

Then run the following command:

```bash
just build
```

On Linux:

This will create an image of the app in the `target` folder. You can place that anywhere and run `request-sink` in the
bin folder.
If you want the deb package, you can run the following command instead:

```bash
just deb
```

On Windows:

This will create an `msi` file in the `target` folder, which you can run to install `request-sink`.
