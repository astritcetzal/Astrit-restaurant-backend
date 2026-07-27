package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //para que spring sepa que la bd generará la el numero
    @Column(name="id_cliente")
    private Integer idCliente;
    private String nombre;


    @OneToMany(mappedBy="cliente")
    private List<Pedido> pedidos;

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
            return nombre;
        }

        public void setNombre (String nombre){
            this.nombre = nombre;
        }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}