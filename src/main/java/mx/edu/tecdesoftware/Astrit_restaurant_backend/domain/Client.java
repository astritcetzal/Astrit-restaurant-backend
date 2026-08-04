package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Client {
    private Integer clientId;
    private String name;
    @JsonIgnore
    private List<Order> order;
    @JsonIgnore
    private String contrasena;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
