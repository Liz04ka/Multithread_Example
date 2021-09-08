import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CircularQueueTest {
    private CircularQueue makeFullQueue() throws InterruptedException {
        var s = new CircularQueue();
        for (int i = 0; i < CircularQueue.CAPACITY; i++) {
            s.put(i);
        }
        return s;
    }

    private CircularQueue makeFullQueueWithShiftedGetIndex() throws InterruptedException {
        var s = makeFullQueue();
        s.get();
        s.put(42);
        return s;
    }


    @Test
    public void empty_OnEmptyQueue_ReturnsTrue() {
        var s = new CircularQueue();
        assertTrue(s.empty());
        assertFalse(s.full());
    }

    @Test
    public void full_OnFullQueue_ReturnsTrue() throws InterruptedException {
        var s = new CircularQueue();
        for (int i = 0; i < CircularQueue.CAPACITY; i++) {
            s.put(1);
        }
        assertTrue(s.full());
        assertFalse(s.empty());
    }

    @Test
    public void put_FullQueue_ThrowByTimeout() {
        assertThrows(RuntimeException.class, () -> makeFullQueue().put(1));
        assertThrows(RuntimeException.class, () -> makeFullQueueWithShiftedGetIndex().put(1));
    }
}
