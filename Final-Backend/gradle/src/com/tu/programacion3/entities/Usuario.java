package com.tu.programacion3.entities;

import com.tu.programacion3.enums.Rol;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Usuario extends Base {

    // datos del usuario
    private String nombre;
    private String email;
    private String password;

    // rul del ususario dede el enum
    private Rol rol;

    // relacion un usuario muchos pedidos
    private Set<Pedido> pedidos = new HashSet<>();

    // constructor vacio
    public Usuario(){

    }

    // Constructor principal
    public Usuario(Long id, String nombre, String email, String password, Rol rol){
        super(id);
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

   // metodo para agregar pedidos
    public void addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    //tostring
    @Override
    public String toString() {
        return "Usuario: " + nombre + " | Email: " + email + " | Rol: " + rol;

    }
    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        // criterio para email como unico en la vida real
        return Objects.equals(email, usuario.email);
    }

    //hascode
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
