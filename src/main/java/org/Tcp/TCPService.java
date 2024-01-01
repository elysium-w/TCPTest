package org.Tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPService {
    public static final String SERVICE_IP="127.0.0.1";
    public static final int SERVICE_PORT=8080;
    public static final char END_SYMBOL='#';

    public void startService(String ip,int port) throws IOException {
        //监听端口8080
        ServerSocket service = new ServerSocket(port);
        System.out.println("Server: Waiting for connection");
        //接受客户端连接
        Socket socket = service.accept();
        System.out.println("Receive connection apply, Create connection with Client:" + socket.getInetAddress());
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output));
        //模拟接受客户端的连接请求过程

        //第一次握手，接收客户端请求报文
        byte[] request=new byte[10];
        input.read(request);
        if (request[1]==1){
            System.out.println("Client Application: SYN=1");
            //处理客户端请求报文
            request[0]=1;
            //判断syn位后将syn+ack确认报文发送回客户端
            socket.getOutputStream().write(request);
            System.out.println("Server Response: ACK=1");
            //第三次握手，接受客户端确认信息，建立连接
            byte[] finalResponse=new byte[request.length];
            input.read(finalResponse);
            if (finalResponse[3]!=0){
                System.out.println("Server Response: CONNECTION-ESTAB-LISHED");
            }

        }
        //与客户端进行通信

    }
    public void sendAndResponse(){

    }
    public static void main(String[] args) throws IOException {
        TCPService service=new TCPService();
        service.startService(SERVICE_IP,SERVICE_PORT);
    }
}
