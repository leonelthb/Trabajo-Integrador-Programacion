package com.tu.programacion3.entities;

import java.util.Objects;

public class DetallePedido extends Base {

    // Cantidad de productos
    private int cantidad;

    // Precio unitario en el momento de la compra
    private Double precio;

    // relacion muchos a un producto
    private Producto producto;

    // constructor vacio
    public DetallePedido() {

    }
    // Constructor principal
    public DetallePedido(Long id, int cantidad, Double precio, Producto producto){
        super(id);
        this.cantidad = cantidad;
        this.precio = precio;
        this.producto = producto;
    }

    // getter y setters

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // tostring
    @Override
    public String toString() {
        return "Detalle -> " + producto.getNombre() + " x" + cantidad + " ($" + precio + ")";

    }

    // equals -> cuando dos detalles son iguales
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetallePedido)) return false;

        DetallePedido that = (DetallePedido) o;

        // criterio para el mismo producto
        return Objects.equals(producto, that.producto); // no se puede tener dos lineas con el mismo producto en el mismo pedido

    }

    //hashCode
    @Override
    public int hashCode() {
        return Objects.hash(producto);
    }
}
