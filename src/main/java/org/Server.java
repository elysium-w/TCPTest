package org;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
//使用socket模拟tcp通信，这里是tcp的服务端
public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // 创建ServerSocket并监听指定端口
            serverSocket = new ServerSocket(8888);
            System.out.println("服务器已启动，等待客户端连接...");

            // 等待客户端连接
            Socket clientSocket = serverSocket.accept();
            System.out.println("客户端已连接：" + clientSocket.getInetAddress());

            // 获取输入流和输出流
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            // 进行数据交互
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream), true);

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("客户端消息：" + message);

                // 向客户端发送响应
                writer.println("服务器收到消息：" + message);

                if (message.equals("bye")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
