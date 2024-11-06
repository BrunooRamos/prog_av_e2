package application;

import application.tasks.PackagingTask;
import application.tasks.PaymentProcessingTask;
import application.tasks.ShippingTask;
import domain.models.Order;

import java.util.Comparator;
import java.util.concurrent.*;

/**
 * Sistema de Procesamiento de Órdenes.
 * Este sistema gestiona el procesamiento de órdenes de manera secuencial a
 * través de tres fases:
 * pago, empaquetado y envío, utilizando ejecutores y colas de tareas para la
 * sincronización.
 */
public class OrderProcessingSystem {

    // Ejecutores de las tareas (pago, empaquetado y envío)
    private final ExecutorService paymentExecutor;
    private final ExecutorService packagingExecutor;
    private final ExecutorService shippingExecutor;

    // Colas entre las relaciones de las tareas
    private final BlockingQueue<Order> paymentToPackagingQueue;
    private final BlockingQueue<Order> packagingToShippingQueue;

    // Hilos para la gestión de empaquetado y envío
    private final Thread packagingThread;
    private final Thread shippingThread;

    /**
     * Constructor del sistema de procesamiento de órdenes.
     * Inicializa los ejecutores, las colas y los hilos necesarios para procesar las
     * órdenes.
     */
    public OrderProcessingSystem() {
        paymentToPackagingQueue = new PriorityBlockingQueue<>();
        packagingToShippingQueue = new LinkedBlockingQueue<>();

        // Executor de pago con cola de prioridad
        paymentExecutor = new ThreadPoolExecutor(
                5, 5, 0L, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>(100, new Comparator<Runnable>() {
                    @Override
                    public int compare(Runnable r1, Runnable r2) {
                        if (r1 instanceof PaymentProcessingTask && r2 instanceof PaymentProcessingTask) {
                            PaymentProcessingTask p1 = (PaymentProcessingTask) r1;
                            PaymentProcessingTask p2 = (PaymentProcessingTask) r2;
                            return p1.compareTo(p2);
                        }
                        return 0;
                    }
                }));

        // Executor de empaquetado con 5 hilos
        packagingExecutor = Executors.newFixedThreadPool(5);

        // Executor de envío con 5 hilos
        shippingExecutor = Executors.newFixedThreadPool(5);

        // Iniciar hilos de empaquetado y envío
        packagingThread = new Thread(this::startPackaging);
        packagingThread.start();
        shippingThread = new Thread(this::startShipping);
        shippingThread.start();
    }

    /**
     * Envía una orden al sistema de procesamiento, comenzando con la fase de pago.
     * 
     * @param order la orden a ser procesada
     */
    public void submitOrder(Order order) {
        paymentExecutor.submit(new PaymentProcessingTask(order, paymentToPackagingQueue));
    }

    /**
     * Inicia el proceso de empaquetado.
     * Extrae las órdenes de la cola de pago a empaquetado y las envía al ejecutor
     * de empaquetado.
     */
    private void startPackaging() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = paymentToPackagingQueue.take();
                packagingExecutor.submit(new PackagingTask(order, packagingToShippingQueue));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Inicia el proceso de envío.
     * Extrae las órdenes de la cola de empaquetado a envío y las envía al ejecutor
     * de envío.
     */
    private void startShipping() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = packagingToShippingQueue.take();
                shippingExecutor.submit(new ShippingTask(order));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Apaga el sistema de procesamiento de órdenes.
     * Detiene todos los ejecutores y los hilos, esperando a que todas las tareas
     * terminen antes de finalizar.
     */
    public void shutdown() {
        try {
            // Apagar ejecutores
            paymentExecutor.shutdown();
            packagingExecutor.shutdown();
            shippingExecutor.shutdown();

            // Interrumpir hilos de empaquetado y envío
            packagingThread.interrupt();
            shippingThread.interrupt();

            // Esperar a que los hilos y ejecutores terminen
            paymentExecutor.awaitTermination(1, TimeUnit.MINUTES);
            packagingExecutor.awaitTermination(1, TimeUnit.MINUTES);
            shippingExecutor.awaitTermination(1, TimeUnit.MINUTES);
            packagingThread.join();
            shippingThread.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
