# np-week4-5
Network Programming weeks 4-5 lab demo

## Tasks
**1.** Write a simple program so that for each interface it prints out the
hardware (MAC) address in hexadecimal form, the hostname and the hostaddress.

**2.** Create a simple client-server Java program (EchoServer), in which the client and
server are communicating via sockets.

###### The client
At start, it prints out its local address and the port number it is using, then it enters
the following loop: it takes one line of input from the console, sends it to the
server, and prints the server’s response to the screen. If the input line is a single
‘X’ character, the client exits after receiving it back from the server.

###### The server
At start, it prints the peer’s socket address to the console (so you can check whom
the server is connected to), and then echoes messages back to the client.

**3.** Protection against communication errors
To make the communication more secure, checksums are used in the communication.

###### The client
It calculates the checksum for each input line from the console, and sends it to the
server via a separate socket connection. It also prints the checksum to the screen.

###### The server
It calculates the checksum of the received data, and prints it to the screen together
with the checksum sent by the client, and a message indicating whether the text
message arrived correctly or not.
