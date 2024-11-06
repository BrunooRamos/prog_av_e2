package domain.models;

import static org.junit.jupiter.api.Assertions.*;

import domain.enums.OrderState;
import org.junit.jupiter.api.Test;

import domain.models.Order;

// Test para la clase Order
public class OrderTest {

    // Test para la creación de una orden
    @Test
    public void testOrderCreation() {
        Order order = new Order(1, true);
        // Verificar que los atributos se hayan asignado correctamente
        assertEquals(1, order.getId());
        assertTrue(order.isUrgent());
        // Verificar que el estado inicial sea "PENDING"
        assertEquals(OrderState.PENDING, order.getState());

    }
    // Test para la comparación entre órdenes con distinta urgencia
    @Test
    public void testOrderComparisonUrgentFirst() {
        Order urgentOrder = new Order(1, true);
        Order normalOrder = new Order(2, false);
        assertTrue(urgentOrder.compareTo(normalOrder) < 0);
    }

    // Test para la comparación entre órdenes con la misma urgencia
    @Test
    public void testOrderComparisonSameUrgency() {
        Order order1 = new Order(1, false);
        Order order2 = new Order(2, false);
        assertEquals(0, order1.compareTo(order2));
    }
}
