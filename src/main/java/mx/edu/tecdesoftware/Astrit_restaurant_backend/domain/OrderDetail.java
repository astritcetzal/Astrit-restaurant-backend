package mx.edu.tecdesoftware.Astrit_restaurant_backend.domain;

public class OrderDetail {
    private Integer productId;
    private Integer amount;
    private double total;
    /*private Boolean Agregar a bd*/

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
