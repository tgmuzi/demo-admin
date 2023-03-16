package com.example.demo.modules.quartz.task;

public interface ScheduledOfTask extends Runnable{

    void execute();

    @Override
    default void run() {
        execute();
    }
}
