package com.tu.programacion3;

import com.tu.programacion3.datos.DatosPruebas;
import com.tu.programacion3.entities.Categoria;
import com.tu.programacion3.entities.Producto;
import com.tu.programacion3.repository.CategoriaRepository;
import com.tu.programacion3.repository.ProductoRepository;
import com.tu.programacion3.repository.UsuarioRepository;
import com.tu.programacion3.utils.JPAUtil;
import java.util.Optional;

//import com.tu.programacion3.repository.PedidoRepository;

import java.util.Scanner;



public class Main {

    private static Object nombre1;

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

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== GESTIÓN DE PRODUCTOS =====");
            System.out.println("1. Alta");
            System.out.println("2. Modificar");
            System.out.println("3. Baja lógica");
            System.out.println("4. Listar");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1: {

                    // ==========================
                    // LISTAR CATEGORÍAS ACTIVAS
                    // ==========================

                    var categorias = categoriaRepo.listarActivos();

                    // Si no existen categorías no se puede crear un producto
                    if (categorias.isEmpty()) {

                        System.out.println("No existen categorías activas. Debe crear una categoría primero.");
                        break;

                    }

                    // Mostrar las categorías disponibles
                    System.out.println("\n===== CATEGORÍAS DISPONIBLES =====");

                    System.out.printf("%-5s %-20s%n", "ID", "NOMBRE");
                    System.out.println("--------------------------------------");

                    categorias.forEach(cat ->
                            System.out.printf("%-5d %-20s%n",
                                    cat.getId(),
                                    cat.getNombre())
                    );

                    // ==========================
                    // SELECCIONAR CATEGORÍA
                    // ==========================

                    System.out.print("\nIngrese el ID de la categoría: ");
                    Long idCategoria = scanner.nextLong();

                    Optional<Categoria> categoriaOptional =
                            categoriaRepo.buscarPorId(idCategoria);

                    if (categoriaOptional.isEmpty()) {

                        System.out.println("La categoría seleccionada no existe.");
                        break;

                    }

                    // Limpiar buffer
                    scanner.nextLine();

                    // ==========================
                    // PEDIR DATOS DEL PRODUCTO
                    // ==========================

                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();

                    // Validar nombre obligatorio
                    if (nombre.isBlank()) {

                        System.out.println("El nombre es obligatorio.");
                        break;

                    }

                    // Verificar que no exista otro producto con ese nombre
                    Producto existente = productoRepo.buscarPorNombre(nombre);

                    if (existente != null) {

                        System.out.println("Ya existe un producto con ese nombre.");
                        break;

                    }

                    System.out.print("Descripción: ");
                    String descripcion = scanner.nextLine();

                    System.out.print("Precio: ");
                    Double precio = scanner.nextDouble();

                    // Validar precio
                    if (precio <= 0) {

                        System.out.println("El precio debe ser mayor a 0.");
                        break;

                    }

                    System.out.print("Stock: ");
                    int stock = scanner.nextInt();

                    // Validar stock
                    if (stock < 0) {

                        System.out.println("El stock no puede ser negativo.");
                        break;

                    }

                    // Limpiar buffer
                    scanner.nextLine();

                    System.out.print("Imagen (opcional): ");
                    String imagen = scanner.nextLine();

                    System.out.print("¿Disponible? (S/N) [Enter = Sí]: ");
                    String respuesta = scanner.nextLine();

                    // Por defecto el producto estará disponible
                    boolean disponible = true;

                    if (!respuesta.isBlank()) {

                        disponible = respuesta.equalsIgnoreCase("S");

                    }

                    // ==========================
                    // CREAR PRODUCTO
                    // ==========================

                    Producto producto = Producto.builder()
                            .nombre(nombre)
                            .descripcion(descripcion)
                            .precio(precio)
                            .stock(stock)
                            .imagen(imagen)
                            .disponible(disponible)
                            .categoria(categoriaOptional.get())
                            .build();

                    // Guardar el producto en la base de datos
                    productoRepo.guardar(producto);

                    // ==========================
                    // CONFIRMAR OPERACIÓN
                    // ==========================

                    System.out.println("\nProducto guardado correctamente.");
                    System.out.println("ID generado: " + producto.getId());
                    System.out.println("Categoría asignada: " + categoriaOptional.get().getNombre());

                    break;
                }

                case 2: {

                    // Solicitar el ID del producto
                    System.out.print("Ingrese el ID del producto a modificar: ");
                    Long idModificar = scanner.nextLong();

                    // Buscar el producto
                    Optional<Producto> productoOptional = productoRepo.buscarPorId(idModificar);

                    if (productoOptional.isEmpty()) {

                        System.out.println("No existe un producto con ese ID.");
                        break;

                    }

                    Producto productoModificar = productoOptional.get();

                    // Limpiar buffer
                    scanner.nextLine();

                    // Mostrar los datos actuales
                    System.out.println("\n===== DATOS ACTUALES =====");
                    System.out.println("Nombre: " + productoModificar.getNombre());
                    System.out.println("Descripción: " + productoModificar.getDescripcion());
                    System.out.println("Precio: " + productoModificar.getPrecio());
                    System.out.println("Stock: " + productoModificar.getStock());
                    System.out.println("Imagen: " + productoModificar.getImagen());
                    System.out.println("Disponible: " + (productoModificar.isDisponible() ? "Sí" : "No"));
                    System.out.println("Categoría: " + productoModificar.getCategoria().getNombre());

                    // Pedir nuevos datos
                    System.out.print("\nNuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();

                    // Validar nombre repetido
                    Producto existente = productoRepo.buscarPorNombre(nuevoNombre);

                    if (existente != null && !existente.getId().equals(productoModificar.getId())) {

                        System.out.println("Ya existe otro producto con ese nombre.");
                        break;

                    }

                    System.out.print("Nueva descripción: ");
                    String nuevaDescripcion = scanner.nextLine();

                    System.out.print("Nuevo precio: ");
                    Double nuevoPrecio = scanner.nextDouble();

                    if (nuevoPrecio <= 0) {

                        System.out.println("El precio debe ser mayor a 0.");
                        break;

                    }

                    System.out.print("Nuevo stock: ");
                    int nuevoStock = scanner.nextInt();

                    if (nuevoStock < 0) {

                        System.out.println("El stock no puede ser negativo.");
                        break;

                    }

                    scanner.nextLine();

                    System.out.print("Nueva imagen: ");
                    String nuevaImagen = scanner.nextLine();

                    System.out.print("¿Disponible? (S/N): ");
                    String respuesta = scanner.nextLine();

                    boolean disponible = respuesta.equalsIgnoreCase("S");

                    // Mostrar categorías
                    System.out.println("\n===== CATEGORÍAS =====");

                    categoriaRepo.listarActivos().forEach(cat ->
                            System.out.printf("%-5d %-20s%n",
                                    cat.getId(),
                                    cat.getNombre())
                    );

                    System.out.print("Nuevo ID de categoría: ");
                    Long idCategoria = scanner.nextLong();

                    Optional<Categoria> categoriaOptional = categoriaRepo.buscarPorId(idCategoria);

                    if (categoriaOptional.isEmpty()) {

                        System.out.println("La categoría no existe.");
                        break;

                    }

                    // Actualizar el objeto
                    productoModificar.setNombre(nuevoNombre);
                    productoModificar.setDescripcion(nuevaDescripcion);
                    productoModificar.setPrecio(nuevoPrecio);
                    productoModificar.setStock(nuevoStock);
                    productoModificar.setImagen(nuevaImagen);
                    productoModificar.setDisponible(disponible);
                    productoModificar.setCategoria(categoriaOptional.get());

                    // Guardar cambios
                    productoRepo.actualizar(productoModificar);

                    System.out.println("Producto actualizado correctamente.");

                    break;
                }

                case 3: {

                    // Solicitar el ID del producto
                    System.out.print("Ingrese el ID del producto a eliminar: ");
                    Long idEliminar = scanner.nextLong();

                    // Buscar el producto antes de eliminarlo
                    Optional<Producto> productoOptional = productoRepo.buscarPorId(idEliminar);

                    // Si no existe, informar el error
                    if (productoOptional.isEmpty()) {

                        System.out.println("No existe un producto con ese ID.");
                        break;

                    }

                    // Guardar el nombre para mostrarlo luego
                    String nombreProducto = productoOptional.get().getNombre();

                    // Ejecutar la baja lógica
                    boolean eliminado = productoRepo.eliminarLogico(idEliminar);

                    if (eliminado) {

                        System.out.println("Producto eliminado correctamente.");
                        System.out.println("Producto afectado: " + nombreProducto);

                    } else {

                        System.out.println("No fue posible eliminar el producto.");

                    }

                    break;
                }

                case 4: {

                    System.out.println("\n******** PRODUCTOS ACTIVOS ********");

                    System.out.printf("%-5s %-20s %-10s %-8s %-15s %-20s%n",
                            "ID",
                            "NOMBRE",
                            "PRECIO",
                            "STOCK",
                            "DISPONIBLE",
                            "CATEGORÍA");

                    System.out.println("----------------------------------------------------------------------------");

                    productoRepo.listarActivos().forEach(producto -> {

                        System.out.printf("%-5d %-20s %-10.2f %-8d %-15s %-20s%n",
                                producto.getId(),
                                producto.getNombre(),
                                producto.getPrecio(),
                                producto.getStock(),
                                producto.isDisponible() ? "Sí" : "No",
                                producto.getCategoria().getNombre());

                    });

                    break;
                }

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
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