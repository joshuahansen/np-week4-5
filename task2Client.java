import java.io.*;
import java.net.*;

class task2Client
{
    public static void main(String[] args)
    {
        String serverHostname = "127.0.0.1";
        int serverPort = 10007;
        try {
            Socket echoSocket = new Socket(serverHostname, serverPort);
            PrintWriter output = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));    
            String userInput;
            while ((userInput = keyboardInput.readLine()) != null)
            {
                System.out.println(userInput);
                System.out.println("echo: " + input.readLine());
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
}
