import java.io.*;
import java.net.*;
import java.util.*;
import static java.lang.System.out;
import java.lang.Integer;

//public class ListNIFs 
public class task1
{
    public static void main(String args[]) throws SocketException {
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        
        for (NetworkInterface netIf : Collections.list(nets)) {
            out.printf("Display name: %s\n", netIf.getDisplayName());
            out.printf("Name: %s\n", netIf.getName());
            displayMACAddress(netIf);
            displayHostAddress(netIf);
            displaySubInterfaces(netIf);
            out.printf("\n");
        }
    }

    static void displaySubInterfaces(NetworkInterface netIf) throws SocketException {
        Enumeration<NetworkInterface> subIfs = netIf.getSubInterfaces();
        
        for (NetworkInterface subIf : Collections.list(subIfs)) {
            out.printf("\tSub Interface Display name: %s\n", subIf.getDisplayName());
            out.printf("\tSub Interface Name: %s\n", subIf.getName());
        }
     }

    static void displayMACAddress(NetworkInterface netIf) throws SocketException 
    {
        if(!netIf.isLoopback())
        {
            byte[] macByte = netIf.getHardwareAddress();
            System.out.print("MAC Address: ");
            for(int i = 0; i < macByte.length; ++i)
            {
                int x = macByte[i] & 0xFF;
                System.out.print(Integer.toHexString(x));
                if(i < macByte.length -1)
                    System.out.print(":");
            }
            System.out.println();
        }
    }
    
    static void displayHostAddress(NetworkInterface netIf) throws SocketException
    {
        Enumeration<InetAddress> inetAddresses = netIf.getInetAddresses();
        for(InetAddress address : Collections.list(inetAddresses))
        {
            try {
                    out.printf("Host Name: %s\n", address.getLocalHost().getHostName());
            }catch(UnknownHostException ex)
            {
                System.out.println("Unknown Host: " + ex);
            }
            out.printf("Host Address: %s\n", address);
        }
    }
} 
