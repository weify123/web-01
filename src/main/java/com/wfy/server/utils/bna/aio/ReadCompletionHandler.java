package com.wfy.server.utils.bna.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by weifeiyu on 2017/7/18.
 */
public class ReadCompletionHandler  implements CompletionHandler<Integer, AsynchronousSocketChannel> {

    private ByteBuffer byteBuffer;
    private String remoteName;
    public ReadCompletionHandler(ByteBuffer byteBuffer, String remoteName) {
        this.byteBuffer = byteBuffer;
        this.remoteName = remoteName;
    }

    @Override
    public void completed(Integer result, AsynchronousSocketChannel attachment) {
        if (result <= 0)
            return;

        byteBuffer.flip();
        System.out.println("[" + this.remoteName + "] " + new String(byteBuffer.array()));

        byteBuffer.clear();
        attachment.read(byteBuffer, attachment, this);
    }

    @Override
    public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
        byteBuffer.clear();
        attachment.read(byteBuffer, attachment, this);
    }
}
