package homework;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class BroadcastSender {
    private static DatagramSocket socket = null;
    private static Timeline tt = null;

    public static void init() {
        tt = new Timeline();
        tt.getKeyFrames().add(new KeyFrame(Duration.millis(Constants.SendBroadcastIntervalMillis), e->{
            sendMessage(Constants.BroadcastMessagePrefix + "__" + Constants.username);
        }));
        tt.setCycleCount(Timeline.INDEFINITE);
    }

    private static void sendMessage(String message) {
        while(true) {
            try{
                socket = new DatagramSocket();
                socket.setBroadcast(true);
                break;
            } catch (Exception e) {
                System.err.println("Error initializing broadcast sender");
                System.err.println(e.getMessage());
            }
        }
        try {
            byte[] buffer = message.getBytes();
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, Constants.BroadcastSocketPort);
            socket.send(packet);
            // System.out.println("Send  " + message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    public static void start() {
        tt.play();
    }

    public static void stop() {
        if (tt != null) {
            tt.stop();
        }
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}