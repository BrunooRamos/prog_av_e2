package application.tasks;

import static org.junit.jupiter.api.Assertions.*;
import domain.models.Order;
import org.junit.jupiter.api.Test;
import java.util.concurrent.LinkedBlockingQueue;

// Test para la clase PaymentProcessingTask
public class PaymentProcessingTaskTest {

    @Test
    public void testPaymentProcessingTaskRun() throws InterruptedException {
        Order order = new Order(1, false);
        LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();
        PaymentProcessingTask task = new PaymentProcessingTask(order, queue);

        // Ejecuta la tarea en un hilo separado
        Thread thread = new Thread(task);
        thread.start();
        thread.join();

        // Verifica que el pedido se haya agregado a la cola
        assertEquals(order, queue.take());
    }

    @Test
    public void testPaymentProcessingTaskComparison() {
        Order urgentOrder = new Order(1, true);
        Order normalOrder = new Order(2, false);

        PaymentProcessingTask urgentTask = new PaymentProcessingTask(urgentOrder, null);
        PaymentProcessingTask normalTask = new PaymentProcessingTask(normalOrder, null);

        assertTrue(urgentTask.compareTo(normalTask) < 0);
    }
}
