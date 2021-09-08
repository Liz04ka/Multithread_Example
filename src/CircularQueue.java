import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CircularQueue {
    public static final int CAPACITY = 10;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final int[] items = new int[CAPACITY];
    private int begin = 0; // индекс начала очереди, get() возвращает элемент с этим индексом
    private int end = CAPACITY - 1; // индекс конца очереди, put() кладёт элемент в массив по этому индексу

    public boolean isGetWasLast() {
        return isGetWasLast;
    }

    private boolean isGetWasLast = true;


    private int next(int index) {
        return (index + 1) % CAPACITY;
    }

    public void put(int val) throws InterruptedException {
        lock.lock();
        try {
            while (full()) {
                notFull.await();
            }

            isGetWasLast = false;
            end = next(end);
            items[end] = val;
            printItems();

            // посылаем сигнал, что очередь не пустая
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int get() throws InterruptedException {
        lock.lock();
        try {
            while (empty()) {
                notEmpty.await();
            }

            isGetWasLast = true;
            int val = items[begin];
            begin = next(begin);
            printItems();

            // посылаем сигнал, что очередь неполная
            notFull.signal();
            return val;
        } finally {
            lock.unlock();
        }
    }

    public void printItems() {
        lock.lock();
        try {
            if (empty()) {
                System.out.println("[]");
            }

            int current = begin;
            System.out.print("[");
            while (true) {
                if (current != begin)
                    System.out.print(", ");

                System.out.print(items[current]);
                if (current == end)
                    break;

                current = next(current);
            }
            System.out.println("]");
        } finally {
            lock.unlock();
        }
    }

    public boolean full() {
        return next(next(end)) == begin;
    }

    public boolean empty() {
        return next(end) == begin;
    }
}

