package org.Tcp;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * seq（序号）：TCP连接字节流中每一个字节都会有一个编号，
 *              而本字段的值指的是本报文段所发送数据部分第一个字节的序号。
 * ack（确认号）：表示期望收到的下一个报文段数据部分的第一个字节的编号，
 *                编号为ack-1及以前的字节已经收到。
 * SYN：当本字段为1时，表示这是一个连接请求或者连接接受报文。
 * ACK：仅当本字段为1时，确认号才有效。
 * FIN：用来释放一个连接。当本字段为1时，表示此报文段的发送端数据已发送完毕，
 *      要求释放运输连接。
 */
//实现协议体结构，第一位为syn，第二位为ack，第三位为fin
public class TCPUtil {

    public static int SYN_BIT=0;
    public static int ACK_BIT=0;
    public static int FIN_BIT=0;
    public static int ack=0;
    private static byte[] message;
    private byte[] data;
    public byte[] buildHeader(int ACK_BIT,int SYN_BIT,int FIN_BIT,int ack){
        byte[] header=new byte[4];
        //数据顺序：ACK >> SYN >> FIN >>  ack
        header[0] = (byte) ACK_BIT;
        header[1] = (byte) SYN_BIT;
        header[2] = (byte) FIN_BIT;
        header[3] = (byte) ack;
        return header;
    }
    public byte[] buildMessage(){
        byte[] header=buildHeader(SYN_BIT, ACK_BIT, FIN_BIT, ack);
        // 计算整个数据包的长度
        int headerLength = header.length;
        int dataLength = data != null ? data.length : 0;
        int totalLength = headerLength + dataLength;

        // 构造整个数据包
        byte[] message = new byte[totalLength];

        // 复制头部到数据包
        System.arraycopy(header, 0, message, 0, headerLength);

        // 如果有数据，复制数据到数据包
        if (dataLength > 0) {
            System.arraycopy(data, 0, message, headerLength, dataLength);
        }
        // 将构建好的数据包赋值给类成员变量
        this.message = message;
        return message;
    }
    // 设置数据部分
    public void setData(byte[] data) {
        this.data = data;
    }
    //发送协议头
    public void sendHeader(Socket socket, int syn, int ack, int fin, int ack_com){
        try {
            OutputStream outputStream=socket.getOutputStream();
            TCPUtil tcpUtil=new TCPUtil();
            SYN_BIT = syn;
            ACK_BIT = ack;
            FIN_BIT = fin;
            ack=ack_com;
            byte[] header=tcpUtil.buildHeader(SYN_BIT,ACK_BIT,FIN_BIT,ack);

            outputStream.write(header);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
