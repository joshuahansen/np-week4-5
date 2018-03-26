import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.util.zip.*;

class task2Server
{
    public static void main(String[] args) throws IOException
    {
        //port for server client to transmit data
        int serverPort = 10007;
        //port for client to send checksum
        int cksumServerPort = 10008;
        //create ServerSocket connections
        ServerSocket serverSocket = new ServerSocket(serverPort);
        ServerSocket cksumSocket = new ServerSocket(cksumServerPort);
        
        System.out.println("Server waiting for connection");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Server connected to Client " + clientSocket);
        Socket clientCksumSocket = cksumSocket.accept();
        System.out.println("Server connected to Client Checksum " + clientCksumSocket);
        
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        InputStream clientInputStream = clientSocket.getInputStream();
        //create buffer readers for client input and checksum
        BufferedReader in = new BufferedReader( new InputStreamReader(clientInputStream));
        BufferedReader cksumInput = new BufferedReader(new InputStreamReader(clientCksumSocket.getInputStream()));
        //create checksum on client input stream
        CheckedInputStream cksum = new CheckedInputStream(clientInputStream, new CRC32());
        System.out.println("Get input from client");
        String inputLine;
        //while there is input from client loop
        while ((inputLine = in.readLine()) != null)
        {
            System.out.println("Server: " + inputLine);
            //if input is terminating 'X' character reply to client and break loop
            if(inputLine.equals("X"))
            {
                out.println(inputLine);
                break;
            }
            //calculate checksum from client input
            cksum.getChecksum().update(inputLine.getBytes(), 0, inputLine.length());
            String clientCksum = cksumInput.readLine();
            String serverCksum = Long.toString(cksum.getChecksum().getValue());
            System.out.println("Server Checksum: " + serverCksum);
            System.out.println("Client Checksum: " + clientCksum);
            //compare client and server generated checksums
            if(clientCksum.equals(serverCksum))
                System.out.println("Checksums Match");
            else
                System.out.println("Error during transmition checksums do not match");
            //send reply to client
            out.println(inputLine);
            
        }
        //close connection with client
        System.out.println("Connection Closed");
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
