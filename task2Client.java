import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.util.zip.*;

class task2Client
{
    public static void main(String[] args)
    {
        //server Host name
        String serverHostname = "127.0.0.1";
        //server ports
        int serverPort = 10007;
        int cksumServerPort = 10008;
        //display details for each server port
        displayDetails(serverPort);
        displayDetails(cksumServerPort);
        try {
            //create ServerSocket connections
            Socket echoSocket = new Socket(serverHostname, serverPort);
            Socket cksumSocket = new Socket(serverHostname, cksumServerPort);
            //create wirters to send data to server
            PrintWriter output = new PrintWriter(echoSocket.getOutputStream(), true);
            PrintWriter cksumOutput = new PrintWriter(cksumSocket.getOutputStream(), true);
            //create buffers for keyboard and server inputs
            BufferedReader input = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            InputStream keyboardInputStream = System.in;
            BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(keyboardInputStream));
            //create checksum for input from keyboard
            CheckedInputStream cksum = new CheckedInputStream(keyboardInputStream, new CRC32());
            String userInput;
            //while client inputs data loop
            while ((userInput = keyboardInput.readLine()) != null)
            {
                //print clients input and calculate checksum
                System.out.println("Client: " + userInput);
                cksum.getChecksum().update(userInput.getBytes(), 0, userInput.length());
                System.out.println("Checksum: " + cksum.getChecksum().getValue());
                //send data to server
                output.println(userInput);
                //send checksum to server
                cksumOutput.println(cksum.getChecksum().getValue());
                String server = input.readLine();
                //if server responds with terminating 'X' character break loop 
                if(server.equals("X"))
                {
                    System.out.println("Server: " + server);
                    System.out.println("Close Connection");
                    break;
                }
                else
                    System.out.println("Server: " + server);
            }
            //close connection to server
            output.close();
            input.close();
            keyboardInput.close();
            echoSocket.close();

        }catch(IOException ex)
        {
            System.out.println("Error with input/output: " + ex);
        }
    }
    //display details for a port number
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
    //display host name and address
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
