package fc;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private int port;
    private Cookie cookieManager;

    public Server(int port, String cookieFile) throws IOException {
        this.port = port;
        this.cookieManager = new Cookie(cookieFile);
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started. Listening on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");
            handleClient(clientSocket);
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String request;
        while ((request = in.readLine()) != null) {
            if (request.equals("get-cookie")) {
                String cookie = cookieManager.getRandomCookie();
                out.println("cookie-text " + cookie);
            } else if (request.equals("close")) {
                System.out.println("Client disconnected.");
                break;
            }
        }
        clientSocket.close();
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -cp fortunecookie.jar fc.Server <port> <cookie_file.txt>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String cookieFile = args[1];

        try {
            Server server = new Server(port, cookieFile);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
