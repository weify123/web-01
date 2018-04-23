package com.wfy.server.utils.thread.semaphore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  信号量在操作系统中一般用来管理数量有限的资源.每类资源有一个对应的信号量.
 *  信号量的值表示资源的可用数量.在使用资源时,要先从该信号量上获取一个使用许可.
 *  成功获取许可之后,资源可用数量减1.在持有许可期,使用者可以对获取资源进行操作.
 *  完成对资源的使用之后,需要在信号量上释放一个许可,资源可用数加1,
 *  允许其他使用者获取资源.当资源可用数为0的时候,需要获取资源的线程以阻塞的方式来等待资源变为可用,
 *  或者过段时间之后再检查资源是否变为可用.
 *
 *  在java中有相应的Semaphore实现类,在创建Semaphore类的对象时指定资源的可用数,
 *  通过acquire方法以阻塞式的方式获取许可,
 *  而tryAcquire方法以非阻塞式的方式来获取许可.当需要释放许可时,使用release方法.
 *  Semaphore类也支持同时获取和释放多个资源的许可.
 *  通过acquire方法获取许可的过程是可以被中断的.如果不希望被中断,那么可以使用acquireUninterruptibly方法.
 *  Semaphore也支持在分配许可时使用公平模式,
 *  通过把构造方法的第二个参数设置为true来使用该模式.在公平模式下,当资源可用时,
 *  等待线程按照调用acquire方法申请资源的顺序依次获取许可.在进行资源管理时,
 *  一般使用公平模式,以避免造成线程饥渴问题.需要注意的是获取资源时,
 *  通过synchronized关键词或锁声明同步.这是因为Semaphore类只是一个资源数量的抽象表示,
 *  并不负责管理资源对象本身,可能有多个线程同时获取到资源使用许可,因此需要使用同步机制避免数据竞争.
 * Created by weifeiyu on 2017/5/19.
 */
public class SemaphoreThread {
    /** 可重入锁,对资源列表进行同步 */
    private final ReentrantLock lock = new ReentrantLock();
    /** 信号量 */
    private final Semaphore semaphore;
    /** 可使用的资源列表 */
    private final LinkedList<Object> resourceList = new LinkedList<Object>();

    public SemaphoreThread(Collection<Object> resourceList) {
        this.resourceList.addAll(resourceList);
        this.semaphore = new Semaphore(resourceList.size(), true);
    }

    /**
     * 获取资源
     *
     * @return 可用的资源
     * @throws InterruptedException
     */
    public Object acquire() throws InterruptedException {
        semaphore.acquire();

        lock.lock();
        try {
            return resourceList.pollFirst();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 释放或者归还资源
     *
     * @param resource 待释放或归还的资源
     */
    public void release(Object resource) {
        lock.lock();
        try {
            resourceList.addLast(resource);
        } finally {
            lock.unlock();
        }

        semaphore.release();
    }

    public static void main(String[] args) {
        //准备2个可用资源
        List<Object> resourceList = new ArrayList<Object>();
        resourceList.add("Resource1");
        resourceList.add("Resource2");

        //准备工作任务
        final SemaphoreThread demo = new SemaphoreThread(resourceList);
        Runnable worker = new Runnable() {
            @Override
            public void run() {
                Object resource = null;
                try {
                    //获取资源
                    resource = demo.acquire();
                    System.out.println(Thread.currentThread().getName() + "\twork   on\t" + resource);
                    //用resource做工作
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "\tfinish on\t" + resource);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //归还资源
                    if (resource != null) {
                        demo.release(resource);
                    }
                }
            }
        };

        //启动9个任务
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 9; i++) {
            service.submit(worker);
        }
        service.shutdown();
    }
}
