package com.wfy.server.utils.bna.bio;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by weifeiyu on 2017/7/18.
 */
public class BIOPrintRunnable implements Runnable {

    private DataInputStream dis;
    private String remoteName;

    public BIOPrintRunnable(DataInputStream dis, String remoteName) {
        this.dis = dis;
        this.remoteName = remoteName;
    }

    public void run() {

        while (true) {
            try {
                String msgReceived = dis.readUTF();
                System.out.println(this.remoteName + " : " + msgReceived);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
