package com.tu.programacion3.datos;
import com.tu.programacion3.entities.*;
import com.tu.programacion3.repository.*;

public class DatosPruebas {

    public static void cargarDatos(
            CategoriaRepository categoriaRepo,
            ProductoRepository productoRepo) {




        // =========================
        // CATEGORIAS DE EJEMPLO
        // =========================

        Categoria bebidas = Categoria.builder()
                .nombre("Bebidas")
                .descripcion("Gaseosas, jugos y aguas")
                .build();

        Categoria comidas = Categoria.builder()
                .nombre("Comidas")
                .descripcion("Hamburguesas, pizzas y sandwiches")
                .build();

        Categoria postres = Categoria.builder()
                .nombre("Postres")
                .descripcion("Helados y tortas")
                .build();

// guardar categorias
        categoriaRepo.guardar(bebidas);
        categoriaRepo.guardar(comidas);
        categoriaRepo.guardar(postres);

        // =========================
        // PRODUCTOS DE EJEMPLO
        // =========================

        Producto coca = Producto.builder()
                .nombre("Coca Cola")
                .precio(2500.0)
                .descripcion("Gaseosa cola")
                .stock(50)
                .imagen("coca.jpg")
                .disponible(true)
                .categoria(bebidas)
                .build();

        Producto fanta = Producto.builder()
                .nombre("Fanta")
                .precio(2200.0)
                .descripcion("Gaseosa naranja")
                .stock(30)
                .imagen("fanta.jpg")
                .disponible(true)
                .categoria(bebidas)
                .build();

        Producto hamburguesa = Producto.builder()
                .nombre("Hamburguesa Completa")
                .precio(8500.0)
                .descripcion("Hamburguesa con papas")
                .stock(20)
                .imagen("hamburguesa.jpg")
                .disponible(true)
                .categoria(comidas)
                .build();

        Producto pizza = Producto.builder()
                .nombre("Pizza Muzzarella")
                .precio(12000.0)
                .descripcion("Pizza clasica")
                .stock(15)
                .imagen("pizza.jpg")
                .disponible(true)
                .categoria(comidas)
                .build();

        Producto helado = Producto.builder()
                .nombre("Helado Chocolate")
                .precio(3000.0)
                .descripcion("Helado artesanal")
                .stock(25)
                .imagen("helado.jpg")
                .disponible(true)
                .categoria(postres)
                .build();

        Producto torta = Producto.builder()
                .nombre("Torta Oreo")
                .precio(4500.0)
                .descripcion("Porcion de torta")
                .stock(10)
                .imagen("oreo.jpg")
                .disponible(true)
                .categoria(postres)
                .build();

        // guardar productos
        productoRepo.guardar(coca);
        productoRepo.guardar(fanta);
        productoRepo.guardar(hamburguesa);
        productoRepo.guardar(pizza);
        productoRepo.guardar(helado);
        productoRepo.guardar(torta);

    }
}




