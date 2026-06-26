package com.tu.programacion3;

import com.tu.programacion3.entities.*;
import com.tu.programacion3.enums.Estado;
import com.tu.programacion3.enums.FormaPago;
import com.tu.programacion3.enums.Rol;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
    // set de productos para evitar duplicados
        Set<Producto> productos = new HashSet<>();

        // Crear categorias
        Categoria bebidas = new Categoria(1L, "Bebidas", "Bebidas frias");
        Categoria comida = new Categoria(2L, "Comida", "Comida Rapida");
        Categoria postres = new Categoria(3L, "Postres", "Dulces");

        // Crear productos
        Producto p1 = new Producto(1L, "Coca", 1000.0);
        Producto p2 = new Producto(2L, "Pepsi", 900.0);
        Producto p3 = new Producto(3L, "Hamburguesa", 2500.0);
        Producto p4 = new Producto(4L,"Pizza", 3000.0);
        Producto p5 = new Producto(5L, "Helado", 1500.0);
        Producto p6 = new Producto(6L, "Agua", 800.0);
        Producto p7 = new Producto(7L, "Jugo", 950.0);
        Producto p8 = new Producto(8L, "Papas Fritas", 1200.0);
        Producto p9 = new Producto(9L, "Torta", 2000.0);
        Producto p10 = new Producto(10L, "Cafe", 1100.0);

        // asignar categorias
        p1.setCategoria(bebidas);
        p2.setCategoria(bebidas);
        p6.setCategoria(bebidas);
        p7.setCategoria(bebidas);
        p10.setCategoria(bebidas);

        p3.setCategoria(comida);
        p4.setCategoria(comida);
        p8.setCategoria(comida);

        p5.setCategoria(postres);
        p9.setCategoria(postres);

        // agregar productos al set
        productos.add(p1);
        productos.add(p2);
        productos.add(p3);
        productos.add(p4);
        productos.add(p5);
        productos.add(p6);
        productos.add(p7);
        productos.add(p8);
        productos.add(p9);
        productos.add(p10);

        // usuarios
        Usuario u1 = new Usuario(1L, "Juan", "juan@mail.com", "1234", Rol.USUARIO);
        Usuario u2 = new Usuario(2L, "Ana", "ana@mail.com", "1234", Rol.ADMIN);

        //pedidos
        Pedido ped1 = new Pedido(1L, LocalDate.now(), Estado.PENDIENTE, FormaPago.EFECTIVO);
        Pedido ped2 = new Pedido(2L, LocalDate.now(), Estado.CONFIRMADO, FormaPago.TARJETA);
        Pedido ped3 = new Pedido(3L, LocalDate.now(), Estado.TERMINADO, FormaPago.TRANSFERENCIA);



        //detalles
        DetallePedido d1 = new DetallePedido(1L, 2, p1.getPrecio(), p1);
        DetallePedido d2 = new DetallePedido(2L, 1, p3.getPrecio(), p3);

        DetallePedido d3 = new DetallePedido(3L, 3, p2.getPrecio(), p2);
        DetallePedido d4 = new DetallePedido(4L, 1, p4.getPrecio(), p4);

        DetallePedido d5 = new DetallePedido(5L, 2, p5.getPrecio(), p5);
        DetallePedido d6 = new DetallePedido(6L, 1, p6.getPrecio(), p6);

        // pedidos
        ped1.addDetalle(d1);
        ped1.addDetalle(d2);

        ped2.addDetalle(d3);
        ped2.addDetalle(d4);

        ped3.addDetalle(d5);
        ped3.addDetalle(d6);

        // asignacion de pedidos a usuario
        u1.addPedido(ped1);
        u1.addPedido(ped2);

        u2.addPedido(ped3);

        // producto
        System.out.println("UN PRODUCTO:");
        System.out.println(p1);

        // lista
        System.out.println("\nLISTA DE PRODUCTOS: ");
        productos.forEach(System.out::println);

        // Usuario con mas pedidos
        Usuario mayor = u1.getPedidos().size() > u2.getPedidos().size() ? u1 : u2;

        System.out.println("\nUSUARIO CON MAS PEDIDOS: ");
        System.out.println(mayor);

        System.out.println("PEDIDOS: ");
        mayor.getPedidos().forEach(System.out::println);


        // prueba equals
        System.out.println("\nPRUEBA DE EQUALS: ");

        Producto prueba = new Producto(99L, "Coca", 9999.0);

        System.out.println("Existe en el set? " + productos.contains(prueba));

        Producto p = new Producto();

        p.setNombre("Coca");
        p.setPrecio(1000.0);

        System.out.println(p.getNombre());
        System.out.println(p.getPrecio());

        Producto p = new Producto();

        p.setNombre("Coca");
        p.setPrecio(1000.0);

        System.out.println(p.getNombre());
        System.out.println(p.getPrecio());


        }

    }
