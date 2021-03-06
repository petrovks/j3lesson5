package geekbrains.j3l5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    Semaphore sm;
    public Tunnel(int count) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        sm = new Semaphore(count);

    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                sm.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                sm.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
