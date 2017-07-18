package com.wfy.server.utils.bna.bio;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by weifeiyu on 2017/7/18.
 */
public class BIOSendRunnable implements Runnable {

    private DataOutputStream dos;
    public BIOSendRunnable(DataOutputStream dos) {
        this.dos = dos;
    }

    public void run() {

        while (true) {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String msgToSend = bufReader.readLine();
                dos.writeUTF(msgToSend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
