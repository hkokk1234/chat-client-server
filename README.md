# Chat ClientβServer

A simple multi-client chat application built with **Java**, **TCP sockets**, and **Swing**. A central server accepts client connections and broadcasts messages to every connected client through a desktop graphical interface.

## Features

- Clientβserver communication over TCP
- Support for multiple simultaneous clients
- Real-time message broadcasting
- Separate Swing interfaces for the server and clients
- Server can send messages directly to all connected clients
- Thread-per-client handling on the server
- No external libraries required

## Technologies

- Java
- Java Swing
- Java Sockets (`ServerSocket` and `Socket`)
- Multithreading

## Project Structure

```text
chatapp/
βββ src/
β   βββ ChatServer.java
β   βββ ChatClient.java
βββ bin/                    # Compiled class files
βββ README.md
```

## How It Works

1. `ChatServer` opens port `12345` and waits for incoming connections.
2. Each client connects to `localhost:12345`.
3. The server creates a dedicated `ClientHandler` thread for every client.
4. Messages received from a client are broadcast to all connected clients.
5. The server operator can also type a message and send it to every client.

## Requirements

- JDK 8 or newer
- A terminal, or an IDE such as Visual Studio Code, IntelliJ IDEA, or Eclipse

Verify that Java is installed:

```bash
java -version
javac -version
```

## Compile and Run

Open a terminal in the `chatapp` directory.

### 1. Compile the application

On Windows Command Prompt or PowerShell:

```powershell
if not exist bin mkdir bin
javac -d bin src\ChatServer.java src\ChatClient.java
```

On Git Bash, Linux, or macOS:

```bash
mkdir -p bin
javac -d bin src/ChatServer.java src/ChatClient.java
```

### 2. Start the server

```bash
java -cp bin ChatServer
```

The server window should display:

```text
Server is running...
```

### 3. Start one or more clients

Open a new terminal for each client and run:

```bash
java -cp bin ChatClient
```

Type a message in the field at the bottom of the client window and press **Enter**. To send a message as the server, type it in the server window and press **Enter**.

## Network Configuration

By default, the client connects to the same computer as the server:

```java
private static final String SERVER_ADDRESS = "localhost";
private static final int SERVER_PORT = 12345;
```

To connect from another computer on the same network, replace `localhost` in `ChatClient.java` with the server computer's local IP address, recompile the client, and make sure TCP port `12345` is allowed through the server firewall.

The port can be changed in both source files, but the client and server must use the same value.

## Current Limitations

- Messages are not encrypted.
- There is no authentication or persistent message history.
- The server address and port are configured directly in the source code.
- Closing the server disconnects all active clients.

## Possible Improvements

- Usernames and join/leave notifications
- Private messages and chat rooms
- Connection settings inside the interface
- Message timestamps and persistent history
- TLS encryption and user authentication
- Improved connection and shutdown handling

## License

This project is provided for educational purposes. No license has currently been specified.
