import application.OrderProcessingSystem;
import domain.models.Order;
import utils.Utils;

/*
 * Clase principal que inicia el sistema de procesamiento de ordenes.
 * Se envian 100 ordenes al sistema, 10 de ellas urgentes.
 * Se espera 10 segundos para que el sistema procese las ordenes.
 * Se imprime el tiempo de inicio y fin del proceso.
 * Se apaga el sistema.
 */

public class Main {
    public static void main(String[] args) {

        OrderProcessingSystem system = new OrderProcessingSystem();


        //Tiempo de inicio en formato: HH:MM:SS:mmm
        String startTime = Utils.actualDate();

        // Enviar 100 ordenes al sistema (10 de ellas urgentes)
        for (int i = 1; i <= 100; i++) {
            boolean urgent = (i % 10 == 0);
            Order order = new Order(i, urgent);
            system.submitOrder(order);
        }

        //Tiempo de fin en formato: HH:MM:SS:mmm
        String endTime = Utils.actualDate();


        // Esperar 10 segundos para que el sistema procese las ordenes
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Starting at " + startTime);
        System.out.println("Ending at " + endTime);

        system.shutdown();
    }
}