package com.tu.programacion3.temp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Clase temporal utilizada para almacenar los productos
// seleccionados antes de iniciar la transacción.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoTemp {

    // ID del producto seleccionado
    private Long idProducto;

    // Cantidad solicitada
    private int cantidad;

}