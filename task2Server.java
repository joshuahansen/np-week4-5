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
        serverSocket = new ServerSocket(serverPort);
        Socket clientSocket = null;
        System.out.println("Server waiting for connection");
        clientSocket = serverSocket.accept();
        System.out.println("Server connected to Client " + clientSocket);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        InputStream clientInputStream = clientSocket.getInputStream();
        BufferedReader in = new BufferedReader( new InputStreamReader(clientInputStream));
        CheckedInputStream cksum = new CheckedInputStream(clientInputStream, new CRC32());
        System.out.println("Get input from client");
        String inputLine;
        while ((inputLine = in.readLine()) != null)
        {
            System.out.println("Server: " + inputLine);
            cksum.getChecksum().update(inputLine.getBytes(), 0, inputLine.length());
            System.out.println("Checksum: " + cksum.getChecksum().getValue());
            out.println(inputLine);
            if(inputLine.equals("X"))
            {
                out.println(inputLine);
                break;
            }
        }
        System.out.println("Connection Closed");
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
