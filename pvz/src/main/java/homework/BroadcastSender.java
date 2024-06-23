package homework;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class BroadcastSender {
    private static DatagramSocket socket = null; // DatagramSocket to send broadcast packets
    private static Timeline tt = null; // Timeline for scheduling periodic broadcast messages

    // Initializes the Timeline for sending broadcast messages
    public static void init() {
        tt = new Timeline(); // Create a new Timeline instance
        tt.getKeyFrames().add(new KeyFrame(Duration.millis(Constants.SendBroadcastIntervalMillis), e -> {
            sendMessage(Constants.BroadcastMessagePrefix + "__" + Constants.username); // Send broadcast message with prefix and username
        }));
        tt.setCycleCount(Timeline.INDEFINITE); // Set the timeline to run indefinitely
    }

    // Sends a broadcast message
    private static void sendMessage(String message) {
        // Attempt to initialize the DatagramSocket
        while (true) {
            try {
                socket = new DatagramSocket(); // Create a new DatagramSocket instance
                socket.setBroadcast(true); // Enable broadcast mode
                break; // Exit the loop if socket is successfully initialized
            } catch (Exception e) {
                // Print error messages if initialization fails
                System.err.println("Error initializing broadcast sender");
                System.err.println(e.getMessage());
            }
        }
        try {
            byte[] buffer = message.getBytes(); // Convert message to byte array
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255"); // Broadcast address
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, Constants.BroadcastSocketPort); // Create a DatagramPacket with the message
            socket.send(packet); // Send the packet
            // System.out.println("Send  " + message); // Uncomment to print sent messages
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an IO exception occurs
        } finally {
            // Close the socket if it is not null and not closed
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    // Starts the broadcast sender by playing the timeline
    public static void start() {
        tt.play(); // Start the timeline to begin sending messages
    }

    // Stops the broadcast sender
    public static void stop() {
        if (tt != null) {
            tt.stop(); // Stop the timeline if it is not null
        }
        // Close the socket if it is not null and not closed
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
