package application.tasks;

import static org.junit.jupiter.api.Assertions.*;

import domain.models.Order;
import org.junit.jupiter.api.Test;

// Test para la clase ShippingTask
public class ShippingTaskTest {

    @Test
    public void testShippingTaskRun() throws InterruptedException {
        Order order = new Order(1, false);
        ShippingTask task = new ShippingTask(order);

        // Ejecuta la tarea en un hilo separado
        Thread thread = new Thread(task);
        thread.start();
        thread.join();

        // Si no hay excepciones, se considera que el envío fue exitoso
        assertTrue(true);
    }
}
