package application.tasks;

import domain.enums.OrderState;
import domain.models.Order;

import java.util.concurrent.BlockingQueue;

//Simula el empaquetado de un pedido
public class PackagingTask implements Runnable {
    private final Order order; // Orden a empaquetar
    private final BlockingQueue<Order> nextStageQueue; // Cola de pedidos para el proximo paso

    public PackagingTask(Order order, BlockingQueue<Order> nextStageQueue) {
        this.order = order;
        this.nextStageQueue = nextStageQueue;
    }

    @Override
    public void run() {
        try {
            // Simula el empaquetado
            System.out.println("Packaging " + order.toString());

            // Actualiza el estado de la orden
            order.setState(OrderState.PACKAGED);

            Thread.sleep(100); // Simula el tiempo que le llevaría

            // Luego del empaquetado, se agrega a la cola para su envio
            nextStageQueue.put(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
