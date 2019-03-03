package com.example.lzw.android_irext;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.example.lzw.android_irext.MyGlobalThreadPool;

public class MyUDPUtils implements Runnable{
    private InetAddress serverAddress ; //地址
    private String Server_IP = "255.255.255.255"; //发送给整个局域网
    private final int SendPort = 9696; //发送方和接收方需要端口一致
    private DatagramPacket Packet_Receive; //接收数据包
    private DatagramSocket Udp_Socket; //端口
    private static final int MAX_DATA_PACKET_LENGTH = 1024; //网络相关变 最大帧长
    private byte[] Buffer_Receive = new byte[MAX_DATA_PACKET_LENGTH]; //接收数据缓存

    public MyUDPUtils(){
        try
        {
            //端口
            Udp_Socket = new DatagramSocket(SendPort);
            //接收包
            Packet_Receive = new DatagramPacket(Buffer_Receive, MAX_DATA_PACKET_LENGTH);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @brief 发送数据
     * @param data:需要发送的数据
     * @param len:数据字节数据
     * @param ip:ip地址
     */
    public void send(byte[] data, int len, String ip)
    {
        Thread_Send thread_send = new Thread_Send(data, len, ip);
        MyGlobalThreadPool.getGlobalThreadPool().execute(thread_send);    //设置全局线程池
    }

    @Override
    public void run() {
        while (true)
        {
            try
            {
                //接收数据
                Udp_Socket.receive(Packet_Receive);
                //打印接收数据
                if(Packet_Receive.getData()!=null)
                    System.out.println(new String(Buffer_Receive,0,Packet_Receive.getLength()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private class Thread_Send implements Runnable {
        //发送数据缓存
        private byte[] Buffer_Send = new byte[MAX_DATA_PACKET_LENGTH];
        //发送数据包
        private DatagramPacket Packet_Send;

        private String send_IP;

        /**
         * @brief 构造函数
         * @param data:需要发送的数据
         * @param len:数据字节数据
         * @param ip:发送地址
         */
        public Thread_Send(byte[] data, int len, String ip) {
            //发送包
            Packet_Send = new DatagramPacket(Buffer_Send, MAX_DATA_PACKET_LENGTH);
            Packet_Send.setData(data);
            Packet_Send.setLength(len);

            send_IP = ip;
        }

        @Override
        public void run() {
            try {
                Packet_Send.setPort(SendPort);
                Packet_Send.setAddress(InetAddress.getByName(send_IP));
                Udp_Socket.send(Packet_Send);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
