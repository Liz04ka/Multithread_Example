import java.util.concurrent.ThreadLocalRandom;

class Consumer implements Runnable {
    private final Thread thread;
    private final CircularQueue queue;

    public Consumer(CircularQueue queue) {
        this.queue = queue;
        thread = new Thread(this); // передаём Consumer в поток для исполнения run
        thread.start();
    }

    @Override
    public void run() {
        System.out.printf("consumer(%d) starts %n", Thread.currentThread().getId());

        while (true) {
            try {
                System.out.printf("consumer(%d) getting value %n", Thread.currentThread().getId());
                queue.get();
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 400)); // обработка значения требует времени
            } catch (Exception ex) {
                System.out.printf("consumer(%d) stops %n", Thread.currentThread().getId());
                return;
            }
        }
    }
}

