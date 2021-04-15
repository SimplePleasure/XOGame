package com.game.trial.temporary;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ConsumeUsers<T> {


    private LinkedBlockingDeque<T> queue;

    public ConsumeUsers() {
        queue = new LinkedBlockingDeque<>();
    }

    public boolean addEl(T el) {
        return queue.add(el);
    }

    public T getEl() throws InterruptedException {
        return queue.pollFirst(10, TimeUnit.SECONDS);
    }

}
