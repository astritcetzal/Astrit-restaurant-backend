package mx.edu.tecdesoftware.Astrit_restaurant_backend.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name="categoria")
public class Categoria {
    //categoria_id descripcion, estado
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_categoria")
    private Integer idCategoria;
    private String descripcion;
    private boolean estado;

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
