package com.wfy.server.utils.bna.bio.client;


import com.wfy.server.utils.bna.bio.BIOPrintRunnable;
import com.wfy.server.utils.bna.bio.BIOSendRunnable;

import java.io.*;
import java.net.Socket;

/**
 * Created by weifeiyu on 2017/7/18.
 */
public class NormalTcpClient {

    private static final int PORT = 5555;
    private static final String IP_ADDRESS = "localhost";

    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = new Socket(IP_ADDRESS, PORT);
        System.out.println("Connecting to server ip: " + IP_ADDRESS + ", port: " + PORT);
        DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        Thread sendThread = new Thread(new BIOSendRunnable(dout));
        Thread printThread = new Thread(new BIOPrintRunnable(dis, "server"));

        sendThread.start();
        printThread.start();

        sendThread.join();
        printThread.join();

        socket.close();
    }
}
