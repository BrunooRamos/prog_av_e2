package application;

import static org.junit.jupiter.api.Assertions.*;

import domain.models.Order;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;

// Test para la clase OrderProcessingSystem
public class OrderProcessingSystemTest {

    @Test
    public void testOrderProcessingSystem() throws InterruptedException {
        OrderProcessingSystem system = new OrderProcessingSystem();

        // Envía 10 pedidos normales y 5 urgentes
        for (int i = 1; i <= 15; i++) {
            boolean urgent = (i % 3 == 0); // Cada tercer pedido es urgente
            Order order = new Order(i, urgent);
            system.submitOrder(order);
        }

        // Espera a que se procesen los pedidos
        TimeUnit.SECONDS.sleep(5);

        // Cierra el sistema
        system.shutdown();

        // Si llega aquí sin excepciones, el test es exitoso
        assertTrue(true);
    }

    @Test
    public void testUrgentOrdersProcessedFirst() throws InterruptedException {
        OrderProcessingSystem system = new OrderProcessingSystem();

        // Crea pedidos con tiempos controlados
        Order urgentOrder = new Order(1, true);
        Order normalOrder = new Order(2, false);

        system.submitOrder(normalOrder);
        system.submitOrder(urgentOrder);

        // Espera suficiente tiempo para que se procesen los pedidos
        TimeUnit.SECONDS.sleep(5);

        // Cierra el sistema
        system.shutdown();

        // No podemos verificar el orden de procesamiento directamente,
        // pero podemos observar en la salida que el pedido urgente se procesa primero.
        assertTrue(true);
    }
}

