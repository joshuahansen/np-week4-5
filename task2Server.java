import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.util.zip.*;

class task2Server
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = null;
        int serverPort = 10007;
        int cksumServerPort = 10008;
        serverSocket = new ServerSocket(serverPort);
        ServerSocket cksumSocket = new ServerSocket(cksumServerPort);
        Socket clientSocket = null;
        System.out.println("Server waiting for connection");
        clientSocket = serverSocket.accept();
        System.out.println("Server connected to Client " + clientSocket);
        Socket clientCksumSocket = cksumSocket.accept();
        System.out.println("Server connected to Client Checksum " + clientCksumSocket);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        InputStream clientInputStream = clientSocket.getInputStream();
        BufferedReader in = new BufferedReader( new InputStreamReader(clientInputStream));
        BufferedReader cksumInput = new BufferedReader(new InputStreamReader(clientCksumSocket.getInputStream()));
        CheckedInputStream cksum = new CheckedInputStream(clientInputStream, new CRC32());
        System.out.println("Get input from client");
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            System.out.println("Server: " + inputLine);
            out.println(inputLine);
            if(inputLine.equals("X"))
            {
                out.println(inputLine);
                break;
            }
            cksum.getChecksum().update(inputLine.getBytes(), 0, inputLine.length());
            System.out.println("Server Checksum: " + cksum.getChecksum().getValue());
            System.out.println("Client Checksum: " + cksumInput.readLine());
        }
        System.out.println("Connection Closed");
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
