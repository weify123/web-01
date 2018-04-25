package com.wfy.server.Test.doc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: AIOClient.java, v 0.1 2018/1/18 13:01 fuck Exp $$
 */
public class AIOClient {

    public static void main(String[] args) throws Exception {
        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        client.connect(new InetSocketAddress("10.65.100.30", 8000), null,
            new CompletionHandler<Void, Object>() {
                @Override
                public void completed(Void result, Object attachment) {
                    try {
                        client.write(ByteBuffer.wrap("Hello!".getBytes()), null, new CompletionHandler<Integer, Object>() {
                            @Override
                            public void completed(Integer result, Object attachment) {
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                                    @Override
                                    public void completed(Integer result, ByteBuffer buffer) {
                                        buffer.flip();
                                        System.out.println(new String(buffer.array()));
                                        try {
                                            client.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void failed(Throwable exc, ByteBuffer attachment) {

                                    }
                                });
                            }

                            @Override
                            public void failed(Throwable exc, Object attachment) {

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            });
        // 由于主线程马上结束, 这里等待上述处理全部完成
        Thread.sleep(1000);
    }
}
