package com.tu.programacion3.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "productos")
@Builder
// equals y hascode para igualdad de nombre
@EqualsAndHashCode(of = "nombre",  callSuper = false)

// esta clase sera tabla SQL
@Entity
public class Categoria extends Base {

    // atributos propios de la clase
    private String nombre;
    private String descripcion;

    // relacion uno a muchos
    @OneToMany(mappedBy = "categoria") // la FK real esta en Producto

    // se evita el null
    @Builder.Default
    private Set<Producto> productos = new HashSet<>();

}












