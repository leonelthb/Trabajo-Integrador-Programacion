package com.tu.programacion3.dtos;
import com.tu.programacion3.enums.Estado;
import com.tu.programacion3.enums.FormaPago;
import java.time.LocalDate;

public record PedidoDTO (
        LocalDate fecha,
        Estado estado,
        FormaPago formaPago,
        double total
){
}
