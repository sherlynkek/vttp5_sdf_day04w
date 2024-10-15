package fc;

import java.io.*;
import java.net.*;

public class Client {

    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server.");

            String serverResponse;
            String userCommand;
            
            while (true) {
                System.out.println("Enter 'get-cookie' to get a cookie or 'close' to close the connection:");
                userCommand = userInput.readLine();
                out.println(userCommand);

                if (userCommand.equals("close")) {
                    System.out.println("Connection closed.");
                    break;
                }

                serverResponse = in.readLine();
                if (serverResponse != null && serverResponse.startsWith("cookie-text")) {
                    System.out.println("Fortune Cookie: " + serverResponse.substring(12));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -cp fortunecookie.jar fc.Client <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        Client client = new Client(host, port);
        client.start();
    }
}

