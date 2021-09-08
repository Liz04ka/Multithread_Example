public class Warehouse {
    public static void main(String[] args) throws InterruptedException {
        CircularQueue queue = new CircularQueue();

        Producer p = new Producer(queue);
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
    }

}
