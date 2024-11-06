package application.tasks;

import domain.enums.OrderState;
import domain.models.Order;

import java.util.concurrent.BlockingQueue;

// Simula el procesamiento del pago de un pedido.
public class PaymentProcessingTask implements Runnable, Comparable<PaymentProcessingTask> {
    private final Order order; //Esta es simplemente la orden que se va a pagar.
    private final BlockingQueue<Order> nextStageQueue; //Cola para pasar al siguiente paso

    public PaymentProcessingTask(Order order, BlockingQueue<Order> nextStageQueue) {
        this.order = order;
        this.nextStageQueue = nextStageQueue;
    }

    public Order getOrder() {
        return order;
    }

    //Se ejecuta al inicio de la tarea
    @Override
    public void run() {
        try {
            // Simula el pago
            System.out.println("Processing payment for " + order.toString());

            // Actualiza el estado de la orden
            order.setState(OrderState.PAID);
            Thread.sleep(100); // Simula el tiempo que le llevaria hacer el pago
            //Una vex que se procesa se agrega a la cola para el siguiente paso
            nextStageQueue.put(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //Sirve para comparar tareas basado en la urgencia del pedido
    @Override
    public int compareTo(PaymentProcessingTask other) {
        return this.order.compareTo(other.order);
    }
}
