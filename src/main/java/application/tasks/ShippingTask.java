package application.tasks;// Tarea que simula el envio de un pedido

import domain.enums.OrderState;
import domain.models.Order;

public class ShippingTask implements Runnable {
    private final Order order;
    // En este caso no hay BlockingQueue porque no hay mas pasos

    public ShippingTask(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        try {
            // Simula el envío
            System.out.println("Shipping " + order.toString());

            // Actualiza el estado de la orden
            order.setState(OrderState.READY_TO_SHIP);

            Thread.sleep(100); // Simula el tiempo que toma el envio
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
