package geekbrains.j3l5;

import com.sun.tools.javac.Main;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static boolean isWin = false;

    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            MainClass.cdl.await();
            Thread.sleep(500 + (int)(Math.random() * 800));

            System.out.println(this.name + " готов");
            MainClass.cdl.await();
            MainClass.countStart.countDown();
            MainClass.countStart.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (!isWin) {
            System.out.println("Участник #" + this.name + " - WIN");
            isWin = true;
        }
        MainClass.countFinish.countDown();
        try {
            MainClass.countFinish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
