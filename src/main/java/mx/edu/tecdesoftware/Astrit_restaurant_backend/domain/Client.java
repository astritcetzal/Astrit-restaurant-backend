package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain;

import java.util.List;

public class Client {
    private Integer clientId;
    private String name;
    private List<Order> order;
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
