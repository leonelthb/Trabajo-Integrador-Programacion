package com.tu.programacion3.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Categoria extends Base {

    // atributos propios de la clase
    private String nombre;
    private String descripcion;

    //relacion una categoria a muchos productos
    private Set<Producto> productos = new HashSet<>();

    // Constructor vacio
    public Categoria(){}

    // Contructor con datos
    public Categoria(Long id, String nombre, String descripcion) {
        super(id); // llama al constructor de base
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    // getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Producto> getProductos() {
        return productos;
    }
    // metodo para agregar productos a la categoria
    public void addProductos(Producto producto) {
        this.productos.add(producto);
    }

    // tostring > como se muestra por consola
    @Override
    public String toString(){
        return "Categoria: " + nombre + " - " + descripcion;
    }

    // equals -> define cuando dos categorias son iguales
    @Override
    public boolean equals(Object o){
        if (this == o) return true; // mismo objeto
        if (o == null || getClass() != o.getClass()) return false;

        Categoria that = (Categoria) o;

        // Se elige el nombre como criterio de igualdad
        return Objects.equals(nombre, that.nombre);
    }

    // hascode-> siempre consistente con equals
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
