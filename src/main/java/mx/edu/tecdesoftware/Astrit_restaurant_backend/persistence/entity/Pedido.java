package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pedido")
    private Integer idPedido;

    @Column(name="id_cliente")
    private Integer idCliente;
    @Column(name="id_mesa")
    private Integer idMesa;

    @Column(name="metodo_pago")
    private Character metodoPago;
    @ManyToOne
    @JoinColumn(name = "id_cliente", insertable=false, updatable=false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="id_mesa",
    insertable=false, updatable=false)
    private Mesa mesa;

    @OneToMany(mappedBy ="pedido", cascade = CascadeType.ALL)
    private List<DetallePedido> detalles;

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public Character getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(Character metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
        if (detalles != null) {
            for (DetallePedido detalle : detalles) {
                detalle.setPedido(this);
            }
        }
    }
}
