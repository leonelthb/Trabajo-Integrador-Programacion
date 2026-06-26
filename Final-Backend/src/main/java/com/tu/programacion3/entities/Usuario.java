package com.tu.programacion3.entities;

import com.tu.programacion3.enums.Rol;
import lombok.*;
import jakarta.persistence.*;


import java.util.HashSet;
// import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "pedidos")
// igualdad para email
@EqualsAndHashCode(of = "mail", callSuper = false)
@Builder
//sera tabla SQL
@Entity
public class Usuario extends Base {

    // datos del usuario
    private String nombre;
    private String apellido;
    private String mail;
    private String password;
    private String celular;


    // rul del ususario dede el enum
    @Enumerated(EnumType.STRING)
    private Rol rol;

    // relacion un usuario muchos pedidos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Pedido> pedidos = new HashSet<>();

    // metodo para agregar pedidos
    public void addPedido(Pedido pedido) {

        pedidos.add(pedido);

        pedido.setUsuario(this);

    }
    }