package com.wfy.server.Test.doc;

import java.util.concurrent.*;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: TraceThreadPoolExecutor.java, v 0.1 2018/1/5 14:28 fuck Exp $$
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {

    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, Long keepAliveTime,
                                   TimeUnit unit, SynchronousQueue<Runnable> workQueue){
        super(corePoolSize,maximumPoolSize, keepAliveTime,unit,workQueue);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command,clientTrace(),Thread.currentThread().getName()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task,clientTrace(),Thread.currentThread().getName()));
    }

    private Exception clientTrace(){
        return new Exception("Client stack trace");
    }

    private Runnable wrap(final Runnable task, final Exception clientStack, String clientThreadName){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    clientStack.printStackTrace();
                    throw e;
                }
            }
        };
    }
}
