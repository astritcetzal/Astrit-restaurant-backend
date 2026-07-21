package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_producto")
    private Integer idProducto;

    private String nombre;
    //categoria
    @Column(name="id_categoria")
    private Integer idCtegoria;
    //precio
    private double precio;

    @ManyToOne
    @JoinColumn(name="id_categoria", insertable = false, updatable = false)
    private Categoria categoria;


    @OneToMany(mappedBy ="producto", cascade = CascadeType.ALL)
    private List<DetallePedido> producto;

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdCtegoria() {
        return idCtegoria;
    }

    public void setIdCtegoria(Integer idCtegoria) {
        this.idCtegoria = idCtegoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<DetallePedido> getProducto() {
        return producto;
    }

    public void setProducto(List<DetallePedido> producto) {
        this.producto = producto;
    }
}
