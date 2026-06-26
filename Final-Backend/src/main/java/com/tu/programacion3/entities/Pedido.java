package com.tu.programacion3.entities;

import com.tu.programacion3.enums.Estado;
import com.tu.programacion3.enums.FormaPago;
import com.tu.programacion3.interfaces.Calculable;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
// import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"usuario", "detalles"}) // pedido no conoce a usuario
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder

//tabla SQL
@Entity
public class Pedido extends Base implements Calculable {
    // Fecha del Pedido
    private LocalDate fecha;

    // estado del pedido desde el enum
    @Enumerated(EnumType.STRING)
    private Estado estado;

    //forma de pago
    @Enumerated(EnumType.STRING)
    private FormaPago formapago;

    private Double total;

  //
    // un pedidos -> muchos detalles
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // un pedido -> muchos detalles
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<DetallePedido> detalles = new HashSet<>();

    // agregar detalles
    public void addDetallePedido(DetallePedido detalle) {

        // agregar detalle al pedido
        detalles.add(detalle);

        // conectar ambas entidades
        detalle.setPedido(this);

    }

    //calcular el total
    @Override
    public void calcularTotal() {

        this.total = detalles.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();

    }
}


