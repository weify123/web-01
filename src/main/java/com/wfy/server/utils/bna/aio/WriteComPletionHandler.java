package com.wfy.server.utils.bna.aio;

import java.nio.channels.CompletionHandler;

/**
 * Created by weifeiyu on 2017/7/18.
 */
public class WriteComPletionHandler implements CompletionHandler<Integer, Void> {

    @Override
    public void completed(Integer result, Void attachment) {
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        System.out.println("Write failed!!!");
    }
}
