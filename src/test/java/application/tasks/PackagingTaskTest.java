package application.tasks;

import static org.junit.jupiter.api.Assertions.*;

import domain.models.Order;
import org.junit.jupiter.api.Test;
import java.util.concurrent.LinkedBlockingQueue;

// Test para la clase PackagingTask
public class PackagingTaskTest {

    @Test
    public void testPackagingTaskRun() throws InterruptedException {
        Order order = new Order(1, false);
        LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();
        PackagingTask task = new PackagingTask(order, queue);

        // Ejecuta la tarea en un hilo separado
        Thread thread = new Thread(task);
        thread.start();
        thread.join();

        // Verifica que el pedido se haya agregado a la cola
        assertEquals(order, queue.take());
    }
}
