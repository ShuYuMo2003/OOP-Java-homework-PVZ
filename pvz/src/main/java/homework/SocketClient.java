package homework;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Represents a client socket for connecting to a server and sending/receiving messages.
 */
public class SocketClient {
    static Socket socket = null;  // Socket instance for connection
    static BufferedReader in = null;  // Input stream reader for receiving messages
    static PrintWriter out = null;  // Output stream writer for sending messages
    static Thread clientThread = null;  // Thread for handling incoming messages asynchronously

    /**
     * Sends a message to the server.
     * @param message The message to be sent.
     */
    static public void send(String message) {
        out.println(message);  // Send message through PrintWriter
    }

    /**
     * Initializes the socket client by connecting to the server.
     * @return True if initialization succeeds, false otherwise.
     */
    static boolean initialize() {
        try {
            // Create socket connection to the server using Constants.ServerIP and Constants.SocketPort
            socket = new Socket(Constants.ServerIP, Constants.SocketPort);
            System.err.println("try to connect to " + Constants.ServerIP + ":" + Constants.SocketPort);

            // Initialize input stream reader and output stream writer
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start a new thread to handle incoming messages from the server
            clientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String message;
                    try {
                        // Read messages from the input stream until connection is closed
                        while ((message = in.readLine()) != null) {
                            System.out.println("Received: " + message);

                            // Split message by "__" delimiter to process different parts
                            String[] args = message.split("__");
                            if (args.length == 4 && args[3].equals("Plant")) {
                                GlobalControl.MessageQueue.add(message);  // Add message to message queue
                                // Example: uncomment to add new plant based on received message
                                // GlobalControl.addNewPlant(args[0], new MapPosition(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                            }
                            // Example: uncomment to send acknowledgment back to the server
                            // out.write("ok");
                        }
                    } catch (Exception e) {
                        System.err.println("Error reading from socket");
                        System.err.println(e.getMessage());
                    }
                }
            });
            clientThread.start();  // Start the client thread to begin receiving messages

        } catch (Exception e) {
            System.err.println("Error initializing socket client");
            System.err.println(e.getMessage());
            return false;  // Return false if any error occurs during initialization
        }
        return true;  // Return true if initialization is successful
    }
}
