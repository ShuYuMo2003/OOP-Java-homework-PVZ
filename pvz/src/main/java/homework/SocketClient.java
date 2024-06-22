package homework;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    static Socket  socket = null;
    static BufferedReader in = null;
    static PrintWriter out = null;
    static Thread clientThread = null;

    static public void send(String message) {
        out.println(message);
    }

    static boolean initialize() {
        try{
            socket = new Socket(Constants.ServerIP, Constants.SocketPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            clientThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String message;
                    try{
                        while ((message = in.readLine()) != null) {
                            System.out.println("Received: " + message);

                            String[] args = message.split("__");
                            if(args.length == 4 && args[3].equals("Plant")) {
                                GlobalControl.MessageQueue.add(message);
                                // GlobalControl.addNewPlant(args[0],
                                //     new MapPosition(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                            }
                            // out.write("ok");
                        }
                    } catch (Exception e) {
                        System.err.println("Error reading from socket");
                        System.err.println(e.getMessage());
                    }
                }
            });
            clientThread.start();

        } catch (Exception e) {
            System.err.println("Error initializing socket client");
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}
