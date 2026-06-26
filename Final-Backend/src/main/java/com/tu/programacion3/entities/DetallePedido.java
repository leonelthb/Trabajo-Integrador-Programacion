package com.tu.programacion3.entities;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"pedido"})
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
public class DetallePedido extends Base {

    // Cantidad de productos
    private int cantidad;

    // Precio unitario
    private Double precio;

    // Subtotal
    private Double subtotal;

    // Muchos detalles -> un pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    // Muchos detalles -> un producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

}