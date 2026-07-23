package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name="id_cliente")
    private String idCliente;
    private String nombre;

    //¡agregar a bd!
    /*
    @OneToMany(mappedBy="cliente")
    private List<Pedido> pedidos;*/
        public String getIdCliente() {
            return idCliente;
        }
        public void setIdCliente(String idCliente){
            this.idCliente = idCliente;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre (String nombre){
            this.nombre = nombre;
        }
    }