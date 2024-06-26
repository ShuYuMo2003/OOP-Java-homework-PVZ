package com.example;

import java.io.*;
import java.net.*;

public class SimpleClient {
    public static void main(String[] args) {
        String hostname = "localhost"; // 服务器地址
        int port = 12345; // 服务器端口号
        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("已连接到服务器");

            // 获取输入输出流
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            // 从标准输入读取用户输入并发送到服务器
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("服务器响应: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("未知的主机: " + hostname);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("无法获取I/O连接到主机: " + hostname);
            e.printStackTrace();
        }
    }
}