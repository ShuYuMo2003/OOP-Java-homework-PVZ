package homework;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadcastReceiver {
    static DatagramSocket socket = null;
    static Thread listernThread = null;

    public static void init() {
        listernThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listening();
            }
        });
    }

    public static void listening() {
        while(true) {
            try{
                socket = new DatagramSocket(Constants.BroadcastSocketPort, InetAddress.getByName("0.0.0.0"));
                socket.setBroadcast(true);
                break;
            } catch (Exception e) {
                System.err.println("Error initializing broadcast receiver");
                System.err.println(e.getMessage());
            }
        }
        while (true) {
            try{
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                InetAddress senderAddress = packet.getAddress();
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                if(receivedMessage.startsWith(Constants.BroadcastMessagePrefix)) {
                    String message = receivedMessage.toString();
                    Constants.addOrUpdateUser(message.substring(message.indexOf("__") + 2, message.length()),
                                              senderAddress.toString().substring(1, senderAddress.toString().length()));
                }
            } catch (Exception e) {
                System.err.println("Error listening to broadcast");
                System.err.println(e.getMessage());
            } finally {
                if(socket != null && socket.isClosed()) {
                    socket.close();
                }
            }
        }
    }

    public static void start() {
        init();
        listernThread.start();
    }
    public static void stop() {
        if(listernThread != null) {
            listernThread.interrupt();
        }
        if(socket != null && socket.isClosed()) {
            socket.close();
            socket = null;
        }
    }

}