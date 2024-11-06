package domain.models;

import domain.enums.OrderState;

// Esta clase representa un pedido, con un id y un booleano que indica si es urgente o no.
public class Order implements Comparable<Order> {
    private final int id;
    private final boolean urgent;
    private OrderState state;

    public Order(int id, boolean urgent) {
        this.id = id;
        this.urgent = urgent;
        this.state = OrderState.PENDING;
    }

    public int getId() {
        return id;
    }

    public OrderState getState() {
        return state;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setState(OrderState state) {
        this.state = state;
    }


    // Se utiliza para ordenar los pedidos en la cola de prioridad.
    @Override
    public int compareTo(Order other) {
        return Boolean.compare(other.urgent, this.urgent);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", urgent=" + urgent +
                ", state=" + state +
                '}';
    }
}
