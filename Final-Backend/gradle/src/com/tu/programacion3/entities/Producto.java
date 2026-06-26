package com.tu.programacion3.entities;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Producto extends Base {

    // atributos del producto
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;


    // para la relacion muchos productos a una categoria
    private Categoria categoria;

    // el contructor vacio
    public Producto(){

    }

    // constructor basico, el que mas se va usar
    public Producto(Long id, String nombre, Double precio){
        super(id); // se hereda id de la Base
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = true;
    }

    // contructor completo
    public Producto(Long id, String nombre, Double precio, String descripcion,
                    int stock, String imagen, boolean disponible, Categoria categoria){

    }

    // getters y setters


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // metodo toString -> para ver como se imprime en la consola
    @Override
    public String toString(){
        return "Producto: " + nombre + " | Precio: $" + precio + " | Stock: " + stock;
    }

    // equals => define cuando un producto es igual
    @Override
    public boolean equals(Object o){
        if (this == o) return true; // mismo objeto
        if (!(o instanceof Producto)) return false;

        Producto producto = (Producto) o;

        // criterio de igualdad por nombre
        return Objects.equals(nombre, producto.nombre); // dos productos con el mismo nombre
    }
    // hascode debe coincidir con equals
    @Override
    public int hashCode(){
        return Objects.hash(nombre);
    }
}
