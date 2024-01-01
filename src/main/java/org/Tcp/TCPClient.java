package org.Tcp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static org.Tcp.TCPService.*;
import static org.Tcp.TCPUtil.*;


public class TCPClient {

    //三次握手
    public static void connect(String host, int port){
        try {
            //连接到服务端
            Socket client=new Socket(host,port);
            InputStream input=client.getInputStream();
            OutputStream output=client.getOutputStream();
            //第一次握手 发送syn码
            TCPUtil tcpUtil=new TCPUtil();
            byte[] request=tcpUtil.buildHeader(0,1,0,0);;
            output.write(request);
            //第二次握手 接受服务端消息并判断ACK位的值
            byte[] response=new byte[request.length];
            input.read(response);
            if (response[0]==1){
                System.out.println("Client Receive: ACK=1");
            }
            //第三次握手 发送最终确认
            byte[] finalResponse = tcpUtil.buildHeader(1,1,0,1);
            output.write(finalResponse);

            //开始传递信息
            Msg();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //滑动窗口通信
    public static void Msg(){
        int exceptSeqNum=0;
        int windowSize=3;
        //构建完整的报文体
        TCPUtil tcpUtil=new TCPUtil();
        byte[] message=tcpUtil.buildMessage();
        //实现超时重传
        //接收到停止信号之后结束传输，开始四次挥手过程
    }
    //四次挥手
    public static void closeConnect(){

    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        while (true){
//            //System.out.println("Connection Status: " + (isConnected ? "Connected" : "Not Connected"));
//            System.out.println("1. Connect");
//            System.out.println("2. Disconnect");
//            System.out.println("3. Exit");
//            System.out.print("Enter your choice: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume the newline character
//
//            switch (choice) {
//                case 1:
//                    connect(SERVICE_IP,SERVICE_PORT);
//                    break;
//                case 2:
//                    closeConnect();
//                    break;
//                case 3:
//                    System.out.println("Exiting the program.");
//                    scanner.close();
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
//            }
//        }
        connect(SERVICE_IP,SERVICE_PORT);
    }
}
