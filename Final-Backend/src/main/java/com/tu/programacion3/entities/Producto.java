package com.tu.programacion3.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// genera el tostring automaticamente
@ToString
//equals y hashcode usando nombre
@EqualsAndHashCode(of = "nombre", callSuper = false)
@Builder

// indica que esta clase sera una tabla SQL
@Entity
public class Producto extends Base {

    // atributos del producto
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;

    // relacion muchos productos a una categoria
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}