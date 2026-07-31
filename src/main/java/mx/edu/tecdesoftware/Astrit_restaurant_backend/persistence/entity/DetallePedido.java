package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name="detalle_pedido")
public class DetallePedido {
    @EmbeddedId
    private DetallePedidoPK id;
    private int cantidad;
    private double total;

    @ManyToOne
    @MapsId("idPedido")
    @JoinColumn(name="id_pedido", insertable = false, updatable = false)
    private Pedido pedido;
    @ManyToOne
    @MapsId("idProducto")
    @JoinColumn(name="id_producto", insertable = false,updatable = false)
    private Producto producto;

    public DetallePedidoPK getId() {
        return id;
    }

    public void setId(DetallePedidoPK id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
