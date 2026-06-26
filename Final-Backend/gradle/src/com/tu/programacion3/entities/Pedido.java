package com.tu.programacion3.entities;

import com.tu.programacion3.enums.Estado;
import com.tu.programacion3.enums.FormaPago;
import com.tu.programacion3.interfaces.Calculable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;



public class Pedido extends Base implements Calculable {
    // Fecha del Pedido
    private LocalDate fecha;

    // estado del pedido desde el enum
    private Estado estado;

    //forma de pago
    private FormaPago formapago;

    //relacion un pedido muchos detalles
    private Set<DetallePedido> detalles = new HashSet<>();

    // constructor vacio
    public Pedido() {

    }

    // contructor principal
    public Pedido(Long id, LocalDate fecha, Estado estado, FormaPago formapago) {
        super(id);
        this.fecha = fecha;
        this.estado = estado;
        this.formapago = formapago;
    }

    //getters y setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public FormaPago getFormapago() {
        return formapago;
    }

    public void setFormapago(FormaPago formapago) {
        this.formapago = formapago;
    }

    public Set<DetallePedido> getDetalles() {
        return detalles;
    }
    // Metodo para agregar detalle al pedido
    public void setDetalles(Set<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    // metodo para agregar un detalle al pedido
    public void addDetalle(DetallePedido detalle) {
        this.detalles.add(detalle);
    }
    // calcular el total del pedido
    @Override
    public double calcularTotal(){
        return detalles.stream().mapToDouble(d -> d.getCantidad() * d.getPrecio()).sum();
        // esto recorre todos los detalles y suma

    }

    // toString
    @Override
    public String toString(){
        return "Pedido ID: " + id + " | Fecha: " + fecha + " | Estado: " + estado + " | Total: $" + calcularTotal();
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;

        Pedido pedido = (Pedido) o;

        // criterio de id
        return Objects.equals(id, pedido.id);
    }

    // hashcode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
