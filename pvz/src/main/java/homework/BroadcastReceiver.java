package homework;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadcastReceiver {
    static DatagramSocket socket = null; // DatagramSocket to receive broadcast packets
    static Thread listernThread = null; // Thread to handle the listening for broadcasts

    // Initializes the listener thread
    public static void init() {
        listernThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listening(); // Start the listening process
            }
        });
    }

    // Method to listen for broadcast messages
    public static void listening() {
        // Attempt to initialize the DatagramSocket
        while(true) {
            try {
                socket = new DatagramSocket(Constants.BroadcastSocketPort, InetAddress.getByName("0.0.0.0")); // Bind socket to specified port and listen on all network interfaces
                socket.setBroadcast(true); // Enable broadcast mode
                break; // Exit the loop if socket is successfully initialized
            } catch (Exception e) {
                // Print error messages if initialization fails
                System.err.println("Error initializing broadcast receiver");
                System.err.println(e.getMessage());
            }
        }

        // Continuously listen for broadcast messages
        while (true) {
            try {
                byte[] buffer = new byte[1024]; // Buffer to hold incoming data
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length); // DatagramPacket to receive data
                socket.receive(packet); // Receive data into the packet

                InetAddress senderAddress = packet.getAddress(); // Get the address of the sender
                String receivedMessage = new String(packet.getData(), 0, packet.getLength()); // Convert the received data to a string

                // Check if the received message starts with the specified prefix
                if(receivedMessage.startsWith(Constants.BroadcastMessagePrefix)) {
                    String message = receivedMessage.toString(); // Convert received message to string
                    // Add or update the user information based on the received message
                    Constants.addOrUpdateUser(
                        message.substring(message.indexOf("__") + 2, message.length()), // Extract the user information from the message
                        senderAddress.toString().substring(1, senderAddress.toString().length()) // Extract the sender's address
                    );
                }
            } catch (Exception e) {
                // Print error messages if an error occurs during listening
                System.err.println("Error listening to broadcast");
                System.err.println(e.getMessage());
            } finally {
                // Close the socket if it is not null and is closed
                if(socket != null && socket.isClosed()) {
                    socket.close();
                }
            }
        }
    }

    // Start the listener thread
    public static void start() {
        init(); // Initialize the thread
        listernThread.start(); // Start the thread
    }

    // Stop the listener thread and close the socket
    public static void stop() {
        if(listernThread != null) {
            listernThread.interrupt(); // Interrupt the thread if it is not null
        }
        if(socket != null && socket.isClosed()) {
            socket.close(); // Close the socket if it is not null and is closed
            socket = null; // Set the socket to null
        }
    }
}
