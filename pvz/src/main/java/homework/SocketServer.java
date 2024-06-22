package homework;

import java.io.*;
import java.net.*;

import javafx.animation.Timeline;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.EventHandler;



public class SocketServer {
    static ServerSocket serverSocket = null;
    static Thread serverThread = null;
    static Socket clientSocket = null;
    static BufferedReader in = null;
    static PrintWriter out = null;
    static boolean receivedAccept = false;

    public static void initialize(){
        try {
            serverSocket = new ServerSocket(Constants.SocketPort);
            start();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Set Up Server Socket Failed");
            System.exit(1);
        }
    }
    public static void send(String message) {
        out.println(message);
    }
    public static void start() {
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int retryTimes = 0;
                try {
                    while (true) {
                        clientSocket = serverSocket.accept();
                        System.err.println("Accepted connection from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new PrintWriter(clientSocket.getOutputStream(), true);
                        receivedAccept = true;

                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            System.out.println("Received: " + inputLine);

                            String[] args = inputLine.split("__");
                            if(args.length == 4 && args[3].equals("Zombine")) {
                                GlobalControl.MessageQueue.add(inputLine);
                                // GlobalControl.addNewZombine(args[0],
                                //     new MapPosition(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                            }
                            // out.write("ok");
                        }
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    retryTimes += 1;
                    e.printStackTrace();
                    System.err.println("Error in Server Socket");
                    if (retryTimes > 100) {
                        System.err.println("Server Socket Failed");
                        System.exit(1);
                    }
                }
            }
        });
        serverThread.start();
    }
}