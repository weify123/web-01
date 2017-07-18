package com.wfy.server.utils.bna;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by weifeiyu on 2017/7/18.
 */
public class TestChannel {

    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("src\\main\\java\\com\\wfy\\server\\utils\\bna\\test.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        StringBuilder sb = new StringBuilder();
        int length;

        // read to buffer from channel
        while ((length = fileChannel.read(byteBuffer)) > 0) {
            byteBuffer.flip();
            byte[] data = byteBuffer.array();
            sb.append(new String(data, 0, length));
            byteBuffer.clear();
        }
        System.out.println(sb.toString());

        // write to channel from buffer
        fileChannel.write(ByteBuffer.wrap("another hello world !!!!".getBytes()));
    }
}
