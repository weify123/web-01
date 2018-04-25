package com.wfy.server.Test.doc.java8.vo;

import java.util.concurrent.locks.StampedLock;

/**
 * <p>注释</p>;
 *
 * @author weifeiyu
 * @version Id: Point.java, v 0.1 2018/1/19 14:51 fuck Exp $$
 */
public class Point {
    private double x, y;
    private final StampedLock stampedLock = new StampedLock();

    void move(double deltaX, double deltaY){
        long stamp = stampedLock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    double distanceFromOrigin(){
        long stamp = stampedLock.tryOptimisticRead();
        double currentX = x, currentY = y;
        if (!stampedLock.validate(stamp)){
            stamp = stampedLock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                stampedLock.unlock(stamp);
            }
        }
        return Math.sqrt(Math.pow(currentX, 2) + Math.pow(currentY, 2));
    }
}
