import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

class task2Client
{
    public static void main(String[] args)
    {
        String serverHostname = "127.0.0.1";
        int serverPort = 10007;
        displayDetails(serverPort);
        try {
            Socket echoSocket = new Socket(serverHostname, serverPort);
            PrintWriter output = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));    
            String userInput;
            while ((userInput = keyboardInput.readLine()) != null)
            {
                System.out.println("Client: " + userInput);
                output.println(userInput);
                String server = input.readLine();
                if(server.equals("X"))
                {
                    System.out.println("Server: " + server);
                    System.out.println("Close Connection");
                    break;
                }
                else
                    System.out.println("Server: " + server);
            }
            output.close();
            input.close();
            keyboardInput.close();
            echoSocket.close();

        }catch(IOException ex)
        {
            System.out.println("Error with input/output: " + ex);
        }
    }
    
    public static void displayDetails(int port)
    {
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface netIf : Collections.list(nets))
            {
                displayAddress(netIf);
                System.out.println("Port: " + port);
            }
        }catch(SocketException ex)
        {
            System.out.println("Falled to get Network Interface details: " + ex);
        }
    }
    public static void displayAddress(NetworkInterface netIf)
    {
            Enumeration<InetAddress> inetAddresses = netIf.getInetAddresses();
            for(InetAddress address : Collections.list(inetAddresses))
            {
                try {
                    System.out.println("Host Name: " + address.getLocalHost().getHostName());
                }
                catch(UnknownHostException ex)
                {
                    System.out.println("Unknown Host: " + ex);
                }
                System.out.println("Host Address: " + address);
            }
    }
}
