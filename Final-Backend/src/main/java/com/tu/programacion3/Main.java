package com.tu.programacion3;

import com.tu.programacion3.datos.DatosPruebas;
import com.tu.programacion3.entities.Categoria;
import com.tu.programacion3.repository.CategoriaRepository;
import com.tu.programacion3.repository.ProductoRepository;
import com.tu.programacion3.repository.UsuarioRepository;
import com.tu.programacion3.utils.JPAUtil;
import java.util.Optional;

//import com.tu.programacion3.repository.PedidoRepository;

import java.util.Scanner;



public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CategoriaRepository categoriaRepo = new CategoriaRepository();

        ProductoRepository productoRepo = new ProductoRepository();

        UsuarioRepository usuarioRepo = new UsuarioRepository();

        // PedidoRepository pedidoRepo = new PedidoRepository();

//        DatosPruebas.cargarDatos(
//                categoriaRepo,
//                productoRepo
//        );

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

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== GESTIÓN DE CATEGORÍAS =====");
            System.out.println("1. Alta");
            System.out.println("2. Modificar");
            System.out.println("3. Baja lógica");
            System.out.println("4. Listar");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1:

                    scanner.nextLine();

                    System.out.print("Ingrese el nombre de la categoría: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Ingrese la descripción: ");
                    String descripcion = scanner.nextLine();

                    Categoria existente = categoriaRepo.buscarPorNombre(nombre);

                    if (existente != null) {

                        System.out.println("Ya existe una categoría con ese nombre.");
                        break;

                    }

                    Categoria categoria = Categoria.builder()
                            .nombre(nombre)
                            .descripcion(descripcion)
                            .build();

                    categoriaRepo.guardar(categoria);

                    System.out.println("Categoría guardada correctamente.");

                    break;

                case 2:

                    System.out.print("Ingrese el ID de la categoría a modificar: ");
                    Long idModificar = scanner.nextLong();

                    Optional<Categoria> categoriaOptional = categoriaRepo.buscarPorId(idModificar);

                    if (categoriaOptional.isPresent()) {

                        Categoria categoriaModificar = categoriaOptional.get();

                        scanner.nextLine();

                        System.out.println("\n===== DATOS ACTUALES =====");
                        System.out.println("Nombre: " + categoriaModificar.getNombre());
                        System.out.println("Descripción: " + categoriaModificar.getDescripcion());

                        System.out.print("\nNuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();

                        System.out.print("Nueva descripción: ");
                        String nuevaDescripcion = scanner.nextLine();

                        Categoria existente1 = categoriaRepo.buscarPorNombre(nuevoNombre);

                        if (existente1 != null && !existente1.getId().equals(categoriaModificar.getId())) {

                            System.out.println("Ya existe otra categoría con ese nombre.");
                            break;
                        }

                        categoriaModificar.setNombre(nuevoNombre);
                        categoriaModificar.setDescripcion(nuevaDescripcion);

                        categoriaRepo.actualizar(categoriaModificar);

                        System.out.println("Categoría modificada correctamente.");

                    } else {

                        System.out.println("No existe una categoría con ese ID.");

                    }

                    break;

                case 3:

                    System.out.print("Ingrese el ID de la categoría a eliminar: ");
                    Long idEliminar = scanner.nextLong();

                    boolean eliminada = categoriaRepo.eliminarLogico(idEliminar);

                    if (eliminada) {
                        System.out.println("Categoría eliminada correctamente.");
                    } else {
                        System.out.println("No existe una categoría con ese ID.");
                    }

                    break;

                case 4:

                    System.out.println("\n******** CATEGORÍAS ACTIVAS ********");

                    System.out.printf("%-5s %-20s %-35s%n",
                            "ID",
                            "NOMBRE",
                            "DESCRIPCIÓN");

                    System.out.println("--------------------------------------------------------------");

                    categoriaRepo.listarActivos().forEach(categoria1 -> {

                        System.out.printf("%-5d %-20s %-35s%n",
                                categoria1.getId(),
                                categoria1.getNombre(),
                                categoria1.getDescripcion());

                    });

                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
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