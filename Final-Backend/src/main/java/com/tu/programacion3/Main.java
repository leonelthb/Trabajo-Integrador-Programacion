package com.tu.programacion3;

import com.tu.programacion3.datos.DatosPruebas;
import com.tu.programacion3.repository.CategoriaRepository;
import com.tu.programacion3.repository.ProductoRepository;
import com.tu.programacion3.repository.UsuarioRepository;
import com.tu.programacion3.utils.JPAUtil;

//import com.tu.programacion3.repository.PedidoRepository;

import java.util.Scanner;



public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CategoriaRepository categoriaRepo = new CategoriaRepository();

        ProductoRepository productoRepo = new ProductoRepository();

        UsuarioRepository usuarioRepo = new UsuarioRepository();

        // PedidoRepository pedidoRepo = new PedidoRepository();

        DatosPruebas.cargarDatos(
                categoriaRepo,
                productoRepo
        );

        boolean salir = false;

        while (!salir) {

            System.out.println("\n===== SISTEMA DE INVENTARIO =====");
            System.out.println("1. Gestionar Categorias");
            System.out.println("2. Gestionar Productos");
            System.out.println("3. Gestionar Usuarios");
            System.out.println("4. Gestionar Pedidos");
            System.out.println("5. Reportes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    menuCategorias(scanner, categoriaRepo, productoRepo);
                    break;

                case 2:
                    menuProductos(scanner, productoRepo, categoriaRepo);
                    break;

                case 3:
                    menuUsuarios(scanner, usuarioRepo);
                    break;

                case 4:
                    System.out.println("Modulo de pedidos en desarrollo.");
                    break;


                case 5:
                    menuReportes(scanner, productoRepo, categoriaRepo);
                    break;

                case 0:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }


        }


        scanner.close();
        JPAUtil.cerrar();
    }
    // MENU CATEGORIAS
    private static void menuCategorias(
            Scanner scanner,
            CategoriaRepository categoriaRepo,
            ProductoRepository productoRepo) {

    }
    // MENU PRODUCTOS
    private static void menuProductos(
            Scanner scanner,
            ProductoRepository productoRepo,
            CategoriaRepository categoriaRepo) {

    }

    // MENU USUARIOS
    private static void menuUsuarios(
            Scanner scanner,
            UsuarioRepository usuarioRepo) {

    }

    // MENU REPORTES
    private static void menuReportes(
            Scanner scanner,
            ProductoRepository productoRepo,
            CategoriaRepository categoriaRepo) {

    }

}