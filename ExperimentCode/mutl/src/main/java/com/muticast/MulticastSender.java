package com.muticast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastSender {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            String message = "Hello, this is a multicast message!";
            byte[] buffer = message.getBytes();

            InetAddress multicastAddress = InetAddress.getByName("230.0.0.0");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, multicastAddress, 8888);

            socket.send(packet);
            socket.close();

            System.out.println("Multicast message sent.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}