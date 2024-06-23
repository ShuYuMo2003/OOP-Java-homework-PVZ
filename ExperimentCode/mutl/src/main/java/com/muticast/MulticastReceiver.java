package com.muticast;


import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver {
    public static void main(String[] args) {
        try {
            MulticastSocket socket = new MulticastSocket(8888);
            InetAddress multicastAddress = InetAddress.getByName("230.0.0.0");
            socket.joinGroup(multicastAddress);

            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Waiting for multicast messages...");

            while (true) {
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received multicast message: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}