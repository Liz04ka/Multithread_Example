import java.util.concurrent.ThreadLocalRandom;

class Producer implements Runnable {
    private final Thread thread;
    private final CircularQueue queue;

    public Producer(CircularQueue queue) {
        this.queue = queue;
        thread = new Thread(this); // передаём Producer в поток для исполнения run
        thread.start();
    }

    @Override
    public void run() {
        System.out.printf("producer(%d) starts %n", Thread.currentThread().getId());

        int value = 0;

        while (true) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(0, 100)); // производство значения требует времени

                // если после get, то нужно увеличить значение
                if (queue.isGetWasLast()) {
                    value += 1;
                }

                System.out.printf("producer(%d) putting %d %n", Thread.currentThread().getId(), value);
                queue.put(value);
            } catch (Exception ex) {
                System.out.printf("producer(%d) stops %n", Thread.currentThread().getId());
                return;
            }
        }
    }
}
