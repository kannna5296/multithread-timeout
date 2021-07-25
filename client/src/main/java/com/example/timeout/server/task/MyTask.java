package com.example.timeout.server.task;

import java.util.Date;

public class MyTask implements Runnable {

  int number;
  int wait;

  public MyTask(int number, int wait) {
    this.number = number;
    this.wait = wait;
  }

  @Override
  public void run() {
    String threadName = Thread.currentThread().getName();
    System.out.printf(
        "+++ Begin [%d] on %s at %tT +++\n",
        number, threadName, new Date());
    try {
      Thread.sleep(wait);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf(
        "+++ End [%d] on %s at %tT +++\n",
        number, threadName, new Date());
  }
}