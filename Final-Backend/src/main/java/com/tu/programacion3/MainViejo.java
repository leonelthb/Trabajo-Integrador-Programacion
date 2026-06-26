package com.tu.programacion3;
import com.tu.programacion3.datos.DatosPruebas;
import com.tu.programacion3.entities.Categoria;
import com.tu.programacion3.entities.Producto;
import com.tu.programacion3.repository.CategoriaRepository;
import com.tu.programacion3.repository.ProductoRepository;

import java.util.Optional;
import java.util.Scanner;

public class MainViejo {

    public static void main(String[] args) {

        // objeto para leer datos por teclado
        Scanner scanner = new Scanner(System.in);

        // repo de categorias
        CategoriaRepository categoriaRepo = new CategoriaRepository();

        // repo de productos
        ProductoRepository productoRepo = new ProductoRepository();

        DatosPruebas.cargarDatos(
                categoriaRepo,
                productoRepo
        );
        // Forzar inicialización de Hibernate
        categoriaRepo.listarActivos();
        // variable para controlar la salida del programa
        boolean salir = false;

        // bucle principal del sistema
        while (!salir) {

            System.out.println("\n===== SISTEMA DE INVENTARIO =====");
            System.out.println("1. Gestionar Categorias");
            System.out.println("2. Gestionar Productos");
            System.out.println("3. Reportes");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    menuCategorias(
                            scanner,
                            categoriaRepo,
                            productoRepo
                    );
                    break;

                case 2:
                    menuProductos(scanner, productoRepo, categoriaRepo);
                    break;

                case 3:
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

        // cerrar scanner
        scanner.close();
    }



    // SUBMENU CATEGORIAS

    private static void menuCategorias(
            Scanner scanner,
            CategoriaRepository categoriaRepo,
            ProductoRepository productoRepo){

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== GESTION DE CATEGORIAS =====");
            System.out.println("1. Alta");
            System.out.println("2. Modificar");
            System.out.println("3. Baja Logica");
            System.out.println("4. Listar");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();

            switch (opcion) { //

                case 1:
                    // limbiar buffer
                    scanner.nextLine();
                    
                    //solicitar datos
                    System.out.println("Nombre: ");
                    String nombre = scanner.nextLine();
                    // descripcion
                    System.out.println("Descripcion: ");
                    String descripcion = scanner.nextLine();

                    // creamos categoria usando el builder
                    Categoria categoria = Categoria.builder()
                            .nombre(nombre)
                            .descripcion(descripcion)
                            .build();

                    // guardar en la bas de datos
                    categoriaRepo.guardar(categoria);

                    System.out.println("Categoria guardada correctamente");

                    break;

                case 2:
                    // pedir id de la categoria
                    System.out.println("Ingrese el ID de la categoria que quiere modificar: ");
                    Long idModificar = scanner.nextLong();

                    //buscar la categoria
                    Optional<Categoria> categoriaOptional = categoriaRepo.buscarPorId(idModificar);

                    //verificacion por si existe
                    if(categoriaOptional.isPresent()) {

                        //obtener la cateogria
                        Categoria categoriaModificar = categoriaOptional.get();

                        //limpiar el buffer
                        scanner.nextLine();

                        // solicitud a los nuevos datos
                        System.out.println("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();

                        System.out.println("Nueva descripcion: ");
                        String nuevaDescripcion = scanner.nextLine();

                        // actualiazado de datos
                        categoriaModificar.setNombre(nuevoNombre);
                        categoriaModificar.setDescripcion(nuevaDescripcion);

                        // guardar cambios
                        categoriaRepo.actualizar(categoriaModificar);

                        System.out.println("Categoria modificada correctamente");

                    }else{
                        System.out.println("No existe una categoria con ese ID");
                    }
                    break;

                case 3:

                    System.out.println(
                            "Ingresa el Id de la categoria a eliminar"
                    );

                    Long id = scanner.nextLong();

                    // eliminar productos asociados
                    for (Producto producto :
                            productoRepo.buscarPorCategoria(id)) {

                        producto.setEliminado(true);

                        productoRepo.actualizar(producto);
                    }

                    // eliminar categoria
                    boolean eliminado =
                            categoriaRepo.eliminarLogico(id);

                    if (eliminado) {

                        System.out.println(
                                "Categoria eliminada correctamente"
                        );

                    } else {

                        System.out.println(
                                "Categoria no eliminada correctamente"
                        );
                    }

                    break;

                case 4:

                    System.out.println("\n***** CATEGORIAS ACTIVAS ******");

                    System.out.printf(
                            "%-5s %-20s %-40s%n",
                            "ID",
                            "NOMBRE",
                            "DESCRIPCION"
                    );

                    System.out.println("----------------------------------------------------------------");

                    categoriaRepo.listarActivos().forEach(c ->
                            System.out.printf(
                                    "%-5d %-20s %-40s%n",
                                    c.getId(),
                                    c.getNombre(),
                                    c.getDescripcion()
                            )
                    );

                    break;



                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Opcion invalida");
            }
        }
    }


    // SUBMENU PRODUCTOS

    private static void menuProductos(Scanner scanner, ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== GESTION DE PRODUCTOS =====");
            System.out.println("1. Alta");
            System.out.println("2. Modificar");
            System.out.println("3. Baja Logica");
            System.out.println("4. Listar");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    // limpiar el buffer
                    scanner.nextLine();

                    //solicitar datos del producto
                    System.out.println("Nonbre: ");
                    String nombre = scanner.nextLine();

                    System.out.println("Precio: ");
                    Double precio = scanner.nextDouble();

                    scanner.nextLine();

                    System.out.println("Stock: ");
                    int stock = scanner.nextInt();

                    scanner.nextLine();
                    System.out.println("Disponible (true/false): ");
                    boolean disponible = scanner.nextBoolean();

                    //mostrar categorias disponibles
                    System.out.println("\n****** Categorais Disponibles ******");

                    categoriaRepo.listarActivos().forEach(c -> System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre()));

                    // pedir categoria
                    System.out.println("Ingrese el ID de la categoria: ");
                    Long categoriaId = scanner.nextLong();

                    Optional<Categoria> categoriaOptional = categoriaRepo.buscarPorId(categoriaId);

                    if(categoriaOptional.isPresent()){

                        Categoria categoria = categoriaOptional.get();

                        Producto producto = Producto.builder()
                                .nombre(nombre)
                                .precio(precio)
                                .stock(stock)
                                .disponible(disponible)
                                .categoria(categoria)
                                .build();

                        productoRepo.guardar(producto);

                        System.out.println("Producto guardado correctamente");


                    }else {
                        System.out.println("Ingrese el Id del producto para modificar");
                        Long idProducto = scanner.nextLong();

                        //buscar el producto
                        Optional<Producto> productoOptional = productoRepo.buscarPorId(idProducto);

                        //verificar si existe
                        if(productoOptional.isPresent()) {
                            Producto productoModificar = productoOptional.get();

                            //limpiar el buffer
                            scanner.nextLine();

                            // nuevos datos
                            System.out.println("Nombre nuevo: ");
                            String nuevoNombre = scanner.nextLine();

                            System.out.println("Nuevo precio: ");
                            Double nuevoPrecio = scanner.nextDouble();

                            scanner.nextLine();

                            System.out.println("Nueva descripcion: ");
                            String nuevoDescripcion = scanner.nextLine();

                            System.out.println("Stock: ");
                            int  nuevoStock = scanner.nextInt();

                            scanner.nextLine();

                            System.out.println("Imagen: ");
                            String nuevoImagen = scanner.nextLine();

                            System.out.println("Disponible (true/false): ");
                            boolean disponibleNuevo = scanner.nextBoolean();

                            //actualizacion de datos
                            productoModificar.setNombre(nuevoNombre);
                            productoModificar.setPrecio(nuevoPrecio);
                            productoModificar.setDescripcion(nuevoDescripcion);
                            productoModificar.setStock(nuevoStock);
                            productoModificar.setImagen(nuevoImagen);
                            productoModificar.setDisponible(disponibleNuevo);

                            //guardar los cambios
                            productoRepo.actualizar(productoModificar);
                            System.out.println("Producto actualizado correctamente");
                        }else{
                            System.out.println("No existe un producto con ese ID");
                        }
                    }
                    break;

                case 2:

                    System.out.println("Ingrese el ID del producto para modificar");
                    Long idProducto = scanner.nextLong();

                    // buscar el producto
                    Optional<Producto> productoOptional =
                            productoRepo.buscarPorId(idProducto);

                    // verificar si existe
                    if (productoOptional.isPresent()) {

                        Producto productoModificar =
                                productoOptional.get();

                        // limpiar buffer
                        scanner.nextLine();

                        System.out.println("Nuevo nombre:");
                        String nuevoNombre = scanner.nextLine();

                        System.out.println("Nuevo precio:");
                        Double nuevoPrecio = scanner.nextDouble();

                        System.out.println("Nuevo stock:");
                        int nuevoStock = scanner.nextInt();

                        scanner.nextLine();
                        System.out.println("Disponible (true/false):");
                        boolean disponibleNuevo = scanner.nextBoolean();

                        // actualizar datos
                        productoModificar.setNombre(nuevoNombre);
                        productoModificar.setPrecio(nuevoPrecio);
                        productoModificar.setStock(nuevoStock);
                        productoModificar.setDisponible(disponibleNuevo);

                        // guardar cambios
                        productoRepo.actualizar(productoModificar);

                        System.out.println(
                                "Producto actualizado correctamente"
                        );

                    } else {

                        System.out.println(
                                "No existe un producto con ese ID"
                        );
                    }

                    break;

                case 3:
                    System.out.println("Ingrese el ID del producto a eliminar: ");
                    Long idEliminar = scanner.nextLong();

                    boolean eliminado = productoRepo.eliminarLogico(idEliminar);

                    if(eliminado){
                        System.out.println("Producto eliminado correctamente");

                    } else {
                        System.out.println("No existe un producto con ese ID");
                    }
                    break;
                case 4:

                    System.out.println("\n***** PRODUCTOS ACTIVOS ******");

                    System.out.printf(
                            "%-5s %-25s %-10s %-10s %-15s%n",
                            "ID",
                            "NOMBRE",
                            "PRECIO",
                            "STOCK",
                            "CATEGORIA"
                    );

                    System.out.println("---------------------------------------------------------------");

                    productoRepo.listarActivos().forEach(p ->
                            System.out.printf(
                                    "%-5d %-25s %-10.2f %-10d %-15s%n",
                                    p.getId(),
                                    p.getNombre(),
                                    p.getPrecio(),
                                    p.getStock(),
                                    p.getCategoria().getNombre()
                            )
                    );

                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Opcion invalida");
            }
        }
    }


    // SUBMENU REPORTES

    private static void menuReportes(Scanner scanner, ProductoRepository productoRepo, CategoriaRepository categoriaRepo) {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== REPORTES =====");
            System.out.println("1. Productos por Categoria");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opcion: ");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    System.out.println("\n***** Categorias Disponibles ******");
                    categoriaRepo.listarActivos().forEach(c -> System.out.println("ID: " + c.getId() + " | Nombre: " + c.getNombre()));
                    System.out.println("Ingrese el Id de la Cateogoria");
                    Long categoriaId = scanner.nextLong();
                    System.out.println("\n***** Productos de la Categoria *****");
                    productoRepo.buscarPorCategoria(categoriaId).forEach(p -> System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Precio: " + p.getPrecio() + " | Stock: " + p.getStock()));

                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Opcion invalida");
            }
        }
    }




}