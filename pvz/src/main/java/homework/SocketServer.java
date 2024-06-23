package homework;

import java.io.*;
import java.net.*;

/**
 * Represents a server socket for receiving messages from clients.
 */
public class SocketServer {
    static ServerSocket serverSocket = null; // Server socket instance
    static Thread serverThread = null; // Thread for handling client connections
    static Socket clientSocket = null; // Socket for client connection
    static BufferedReader in = null; // Reader for receiving messages from client
    static PrintWriter out = null; // Writer for sending messages to client
    static boolean receivedAccept = false; // Flag indicating if connection was accepted

    /**
     * Initializes the server socket and starts accepting client connections.
     */
    public static void initialize() {
        try {
            // Create server socket with the specified port number
            serverSocket = new ServerSocket(Constants.SocketPort);
            start(); // Start accepting client connections
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Set Up Server Socket Failed");
            System.exit(1); // Exit the program if server socket setup fails
        }
    }

    /**
     * Sends a message to the connected client.
     * @param message The message to send.
     */
    public static void send(String message) {
        out.println(message); // Send message through PrintWriter
    }

    /**
     * Starts accepting client connections and handles incoming messages.
     */
    public static void start() {
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int retryTimes = 0; // Counter for retry attempts on socket errors
                try {
                    while (true) { // Continue accepting connections indefinitely
                        clientSocket = serverSocket.accept(); // Accept incoming client connection
                        System.err.println("Accepted connection from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Setup input stream reader
                        out = new PrintWriter(clientSocket.getOutputStream(), true); // Setup output stream writer
                        receivedAccept = true; // Mark connection as accepted

                        String inputLine;
                        while ((inputLine = in.readLine()) != null) { // Read messages from client
                            System.out.println("Received: " + inputLine);

                            // Split received message by "__" delimiter to process different parts
                            String[] args = inputLine.split("__");
                            if (args.length == 4 && args[3].equals("Zombine")) {
                                GlobalControl.MessageQueue.add(inputLine); // Add message to message queue
                                // Example: uncomment to add new zombine based on received message
                                // GlobalControl.addNewZombine(args[0], new MapPosition(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                            }
                            // Example: uncomment to send acknowledgment back to the client
                            // out.write("ok");
                        }
                        in.close(); // Close input stream
                        clientSocket.close(); // Close client socket
                    }
                } catch (IOException e) {
                    retryTimes += 1; // Increment retry counter on socket errors
                    e.printStackTrace();
                    System.err.println("Error in Server Socket");
                    if (retryTimes > 100) { // Exit if retry limit is exceeded
                        System.err.println("Server Socket Failed");
                        System.exit(1);
                    }
                }
            }
        });
        serverThread.start(); // Start the server thread to begin accepting connections
    }
}
