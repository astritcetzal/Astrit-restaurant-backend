package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain;

import java.util.List;

public class Order {

    private Integer orderId;
    private String clientId;
    private Integer tableId;
    private Character paymentMethod;
    private List<OrderDetail> orders;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getIdClient() {
        return clientId;
    }

    public void setIdClient(String idClient) {
        this.clientId = idClient;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Character getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Character paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderDetail> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetail> orders) {
        this.orders = orders;
    }
}
