import java.io.*;
import java.net.*;

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
        BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
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
        }
        System.out.println("Connection Closed");
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
