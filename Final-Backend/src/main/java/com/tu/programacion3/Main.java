package com.tu.programacion3;

import com.tu.programacion3.datos.DatosPruebas;
import com.tu.programacion3.entities.Categoria;
import com.tu.programacion3.entities.Pedido;
import com.tu.programacion3.entities.Producto;
import com.tu.programacion3.entities.Usuario;
import com.tu.programacion3.enums.Estado;
import com.tu.programacion3.enums.Rol;
import com.tu.programacion3.repository.CategoriaRepository;
import com.tu.programacion3.repository.ProductoRepository;
import com.tu.programacion3.repository.UsuarioRepository;
import com.tu.programacion3.temp.ItemPedidoTemp;
import com.tu.programacion3.utils.JPAUtil;
import java.util.Optional;
import java.util.ArrayList;
import com.tu.programacion3.repository.PedidoRepository;
import java.util.List;

import com.tu.programacion3.enums.FormaPago;

import java.util.Scanner;



public class Main {

    private static Object nombre1;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CategoriaRepository categoriaRepo = new CategoriaRepository();

        ProductoRepository productoRepo = new ProductoRepository();

        UsuarioRepository usuarioRepo = new UsuarioRepository();

        PedidoRepository pedidoRepo = new PedidoRepository();

//        DatosPruebas.cargarDatos(
//               categoriaRepo,
//               productoRepo
//       );

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
                    menuPedidos(
                            scanner,
                            pedidoRepo,
                            usuarioRepo,
                            productoRepo
                    );
                    break;


                case 5:
                    menuReportes(
                            scanner,
                            productoRepo,
                            categoriaRepo,
                            usuarioRepo,
                            pedidoRepo
                    );
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

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== GESTIÓN DE USUARIOS =====");
            System.out.println("1. Alta");
            System.out.println("2. Modificar");
            System.out.println("3. Baja lógica");
            System.out.println("4. Listar");
            System.out.println("5. Buscar por Email");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1: {

                    // Limpiar el buffer
                    scanner.nextLine();

                    // Solicitar los datos del usuario
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Apellido: ");
                    String apellido = scanner.nextLine();

                    System.out.print("Mail: ");
                    String mail = scanner.nextLine();

                    // Verificar que el mail no exista
                    Usuario existente = usuarioRepo.buscarPorMail(mail);

                    if (existente != null) {

                        System.out.println("Ya existe un usuario con ese mail.");
                        break;

                    }

                    System.out.print("Celular: ");
                    String celular = scanner.nextLine();

                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    // Seleccionar el rol
                    System.out.println("\nSeleccione el rol:");
                    System.out.println("1. ADMIN");
                    System.out.println("2. USUARIO");
                    System.out.print("Opción: ");

                    int opcionRol = scanner.nextInt();

                    Rol rol = Rol.USUARIO;

                    switch (opcionRol) {

                        case 1:
                            rol = Rol.ADMIN;
                            break;

                        case 2:
                            rol = Rol.USUARIO;
                            break;

                        default:
                            System.out.println("Rol inválido. Se asignará USUARIO por defecto.");
                    }

                    // Crear el usuario
                    Usuario usuario = Usuario.builder()
                            .nombre(nombre)
                            .apellido(apellido)
                            .mail(mail)
                            .celular(celular)
                            .password(password)
                            .rol(rol)
                            .build();

                    // Guardar en la base de datos
                    usuarioRepo.guardar(usuario);

                    System.out.println("Usuario guardado correctamente.");
                    System.out.println("ID generado: " + usuario.getId());

                    break;
                }

                case 2: {

                    // Mostrar los usuarios activos
                    System.out.println("\n******** USUARIOS ACTIVOS ********");

                    usuarioRepo.listarActivos().forEach(usuario ->

                            System.out.printf("%-5d %-30s %-30s%n",
                                    usuario.getId(),
                                    usuario.getNombre() + " " + usuario.getApellido(),
                                    usuario.getMail())

                    );

                    // Solicitar el ID
                    System.out.print("\nIngrese el ID del usuario a modificar: ");
                    Long idModificar = scanner.nextLong();

                    Optional<Usuario> usuarioOptional = usuarioRepo.buscarPorId(idModificar);

                    if (usuarioOptional.isEmpty()) {

                        System.out.println("No existe un usuario con ese ID.");
                        break;

                    }

                    Usuario usuarioModificar = usuarioOptional.get();

                    scanner.nextLine();

                    // Mostrar los datos actuales
                    System.out.println("\n===== DATOS ACTUALES =====");
                    System.out.println("Nombre: " + usuarioModificar.getNombre());
                    System.out.println("Apellido: " + usuarioModificar.getApellido());
                    System.out.println("Mail: " + usuarioModificar.getMail());
                    System.out.println("Celular: " + usuarioModificar.getCelular());
                    System.out.println("Rol: " + usuarioModificar.getRol());

                    // Solicitar los nuevos datos
                    System.out.print("\nNuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();

                    System.out.print("Nuevo apellido: ");
                    String nuevoApellido = scanner.nextLine();

                    System.out.print("Nuevo mail: ");
                    String nuevoMail = scanner.nextLine();

                    // Validar que el mail no pertenezca a otro usuario
                    Usuario existente = usuarioRepo.buscarPorMail(nuevoMail);

                    if (existente != null &&
                            !existente.getId().equals(usuarioModificar.getId())) {

                        System.out.println("Ya existe otro usuario con ese mail.");
                        break;

                    }

                    System.out.print("Nuevo celular: ");
                    String nuevoCelular = scanner.nextLine();

                    System.out.print("Nueva contraseña: ");
                    String nuevaPassword = scanner.nextLine();

                    // Actualizar los datos
                    usuarioModificar.setNombre(nuevoNombre);
                    usuarioModificar.setApellido(nuevoApellido);
                    usuarioModificar.setMail(nuevoMail);
                    usuarioModificar.setCelular(nuevoCelular);
                    usuarioModificar.setPassword(nuevaPassword);

                    // Guardar los cambios
                    usuarioRepo.actualizar(usuarioModificar);

                    System.out.println("Usuario actualizado correctamente.");

                    break;
                }

                case 3: {

                    // Solicitar el ID del usuario
                    System.out.print("Ingrese el ID del usuario a eliminar: ");
                    Long idEliminar = scanner.nextLong();

                    // Buscar el usuario antes de eliminarlo
                    Optional<Usuario> usuarioOptional = usuarioRepo.buscarPorId(idEliminar);

                    // Verificar que exista
                    if (usuarioOptional.isEmpty()) {

                        System.out.println("No existe un usuario con ese ID.");
                        break;

                    }

                    // Guardar nombre y apellido para mostrarlos luego
                    Usuario usuarioEliminar = usuarioOptional.get();

                    // Ejecutar la baja lógica
                    boolean eliminado = usuarioRepo.eliminarLogico(idEliminar);

                    if (eliminado) {

                        System.out.println("Usuario eliminado correctamente.");
                        System.out.println("Usuario afectado: "
                                + usuarioEliminar.getNombre() + " "
                                + usuarioEliminar.getApellido());

                        System.out.println("Sus pedidos permanecen en el sistema.");

                    } else {

                        System.out.println("No fue posible eliminar el usuario.");

                    }

                    break;
                }

                case 4: {

                    // Mostrar encabezado
                    System.out.println("\n******** USUARIOS ACTIVOS ********");

                    System.out.printf("%-5s %-30s %-30s %-15s%n",
                            "ID",
                            "NOMBRE",
                            "MAIL",
                            "ROL");

                    System.out.println("--------------------------------------------------------------------------------");

                    // Listar todos los usuarios activos
                    usuarioRepo.listarActivos().forEach(usuario -> {

                        System.out.printf("%-5d %-30s %-30s %-15s%n",
                                usuario.getId(),
                                usuario.getNombre() + " " + usuario.getApellido(),
                                usuario.getMail(),
                                usuario.getRol());

                    });

                    break;
                }

                case 5: {

                    // Limpiar el buffer
                    scanner.nextLine();

                    // Solicitar el mail
                    System.out.print("Ingrese el mail del usuario: ");
                    String mail = scanner.nextLine();

                    // Buscar el usuario
                    Usuario usuario = usuarioRepo.buscarPorMail(mail);

                    if (usuario == null || usuario.isEliminado()) {

                        System.out.println("No existe un usuario activo con ese mail.");
                        break;

                    }

                    // Mostrar los datos (sin la contraseña)
                    System.out.println("\n===== DATOS DEL USUARIO =====");

                    System.out.println("ID: " + usuario.getId());
                    System.out.println("Nombre: " + usuario.getNombre());
                    System.out.println("Apellido: " + usuario.getApellido());
                    System.out.println("Mail: " + usuario.getMail());
                    System.out.println("Celular: " + usuario.getCelular());
                    System.out.println("Rol: " + usuario.getRol());

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


    // MENU PEDIDOS
    private static void menuPedidos(
            Scanner scanner,
            PedidoRepository pedidoRepo,
            UsuarioRepository usuarioRepo,
            ProductoRepository productoRepo) {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== GESTIÓN DE PEDIDOS =====");
            System.out.println("1. Alta de pedido");
            System.out.println("2. Cambiar estado");
            System.out.println("3. Baja lógica");
            System.out.println("4. Listado");
            System.out.println("5. Pedidos por usuario");
            System.out.println("6. Pedidos por estado");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1: {

                    // Obtener los usuarios activos
                    List<Usuario> usuarios = usuarioRepo.listarActivos();

                    if (usuarios.isEmpty()) {

                        System.out.println("No existen usuarios activos.");
                        break;

                    }

                    // Mostrar usuarios
                    System.out.println("\n===== USUARIOS =====");

                    usuarios.forEach(usuario ->
                            System.out.printf("%-5d %-25s%n",
                                    usuario.getId(),
                                    usuario.getNombre() + " " + usuario.getApellido())
                    );

                    // Solicitar el usuario
                    System.out.print("\nIngrese el ID del usuario: ");
                    Long idUsuario = scanner.nextLong();

                    Optional<Usuario> usuarioOptional =
                            usuarioRepo.buscarPorId(idUsuario);

                    if (usuarioOptional.isEmpty()) {

                        System.out.println("Usuario inexistente.");
                        break;

                    }

                    Usuario usuario = usuarioOptional.get();

                    // Elegir la forma de pago
                    System.out.println("\nForma de pago:");
                    System.out.println("1. TARJETA");
                    System.out.println("2. TRANSFERENCIA");
                    System.out.println("3. EFECTIVO");

                    int opcionPago = scanner.nextInt();

                    FormaPago formaPago = FormaPago.EFECTIVO;

                    switch (opcionPago) {

                        case 1:
                            formaPago = FormaPago.TARJETA;
                            break;

                        case 2:
                            formaPago = FormaPago.TRANSFERENCIA;
                            break;

                        case 3:
                            formaPago = FormaPago.EFECTIVO;
                            break;

                        default:
                            System.out.println("Forma de pago inválida.");
                            break;

                    }

                    // Crear la lista temporal
                    List<ItemPedidoTemp> items = new ArrayList<>();

                    System.out.println("\nComenzaremos a agregar productos...");

                    boolean agregarOtro = true;

                    while (agregarOtro) {

                        // Mostrar productos activos
                        System.out.println("\n===== PRODUCTOS DISPONIBLES =====");

                        productoRepo.listarActivos().forEach(producto ->

                                System.out.printf("%-5d %-25s $%-10.2f Stock: %-5d Disponible: %s%n",
                                        producto.getId(),
                                        producto.getNombre(),
                                        producto.getPrecio(),
                                        producto.getStock(),
                                        producto.isDisponible() ? "SI" : "NO")

                        );

                        // Solicitar el producto
                        System.out.print("\nIngrese el ID del producto: ");
                        Long idProducto = scanner.nextLong();

                        Optional<Producto> productoOptional =
                                productoRepo.buscarPorId(idProducto);

                        if (productoOptional.isEmpty()) {

                            System.out.println("Producto inexistente.");
                            continue;

                        }

                        Producto producto = productoOptional.get();

                        // Verificar disponibilidad
                        if (!producto.isDisponible()) {

                            System.out.println("El producto no está disponible.");
                            continue;

                        }

                        // Solicitar cantidad
                        System.out.print("Cantidad: ");
                        int cantidad = scanner.nextInt();

                        if (cantidad <= 0) {

                            System.out.println("La cantidad debe ser mayor a cero.");
                            continue;

                        }

                        if (cantidad > producto.getStock()) {

                            System.out.println("Stock insuficiente.");
                            System.out.println("Stock disponible: " + producto.getStock());
                            continue;

                        }

                        // Guardar en la lista temporal
                        items.add(new ItemPedidoTemp(
                                producto.getId(),
                                cantidad
                        ));

                        // Preguntar si desea agregar otro producto
                        System.out.print("\n¿Agregar otro producto? (S/N): ");

                        scanner.nextLine();

                        String respuesta = scanner.nextLine();

                        agregarOtro = respuesta.equalsIgnoreCase("S");
                    }

                    // Verificar que se haya agregado al menos un producto
                    if (items.isEmpty()) {

                        System.out.println("No se agregaron productos al pedido.");
                        break;

                    }

                    // Crear el pedido
                    Pedido pedido = pedidoRepo.crearPedido(
                            usuario,
                            formaPago,
                            items
                    );

                    // Verificar si se creó correctamente
                    if (pedido != null) {

                        System.out.println("\n===== PEDIDO CREADO =====");
                        System.out.println("ID: " + pedido.getId());
                        System.out.println("Fecha: " + pedido.getFecha());
                        System.out.println("Usuario: "
                                + pedido.getUsuario().getNombre()
                                + " "
                                + pedido.getUsuario().getApellido());
                        System.out.println("Forma de pago: " + pedido.getFormapago());
                        System.out.println("Estado: " + pedido.getEstado());
                        System.out.println("Total: $" + pedido.getTotal());

                    } else {

                        System.out.println("No fue posible crear el pedido.");




                    }

                }

                break;



                case 2: {

                    // Solicitar el ID del pedido
                    System.out.print("Ingrese el ID del pedido: ");
                    Long idModificar = scanner.nextLong();

                    // Buscar el pedido
                    Optional<Pedido> pedidoOptional = pedidoRepo.buscarPorId(idModificar);

                    if (pedidoOptional.isEmpty()) {

                        System.out.println("No existe un pedido con ese ID.");
                        break;

                    }

                    Pedido pedidoModificar = pedidoOptional.get();

                    // Mostrar el estado actual
                    System.out.println("\nEstado actual: " + pedidoModificar.getEstado());

                    // Mostrar las opciones disponibles
                    System.out.println("\nSeleccione el nuevo estado:");
                    System.out.println("1. PENDIENTE");
                    System.out.println("2. CONFIRMADO");
                    System.out.println("3. TERMINADO");
                    System.out.println("4. CANCELADO");

                    int opcionEstado = scanner.nextInt();

                    Estado nuevoEstado;

                    Estado nuevoEstado1 = Estado.PENDIENTE;

                    switch (opcionEstado) {

                        case 1:
                            nuevoEstado = Estado.PENDIENTE;
                            break;

                        case 2:
                            nuevoEstado = Estado.CONFIRMADO;
                            break;

                        case 3:
                            nuevoEstado = Estado.TERMINADO;
                            break;

                        case 4:
                            nuevoEstado = Estado.CANCELADO;
                            break;

                        default:
                            System.out.println("Estado inválido.");
                    }

                    // Actualizar el estado
                    pedidoModificar.setEstado(nuevoEstado1);

                    // Guardar cambios
                    pedidoRepo.actualizar(pedidoModificar);

                    System.out.println("\nEstado actualizado correctamente.");
                    System.out.println("ID Pedido: " + pedidoModificar.getId());
                    System.out.println("Nuevo Estado: " + pedidoModificar.getEstado());

                    break;
                }

                case 3: {

                    // Solicitar el ID del pedido
                    System.out.print("Ingrese el ID del pedido a eliminar: ");
                    Long idEliminar = scanner.nextLong();

                    // Buscar el pedido
                    Optional<Pedido> pedidoOptional = pedidoRepo.buscarPorId(idEliminar);

                    // Verificar que exista
                    if (pedidoOptional.isEmpty()) {

                        System.out.println("No existe un pedido con ese ID.");
                        break;

                    }

                    // Obtener el pedido antes de eliminarlo
                    Pedido pedidoEliminar = pedidoOptional.get();

                    // Ejecutar la baja lógica
                    boolean eliminado = pedidoRepo.eliminarLogico(idEliminar);

                    if (eliminado) {

                        System.out.println("\nPedido eliminado correctamente.");
                        System.out.println("ID del pedido: " + pedidoEliminar.getId());
                        System.out.println("Total: $" + pedidoEliminar.getTotal());

                        System.out.println("\nEl stock de los productos NO fue restaurado.");
                        System.out.println("Los detalles del pedido permanecen en la base de datos.");

                    } else {

                        System.out.println("No fue posible eliminar el pedido.");

                    }

                    break;
                }

                case 4: {

                    // Mostrar encabezado
                    System.out.println("\n******** PEDIDOS ACTIVOS ********");

                    System.out.printf("%-5s %-12s %-15s %-18s %-25s %-10s%n",
                            "ID",
                            "FECHA",
                            "ESTADO",
                            "PAGO",
                            "USUARIO",
                            "TOTAL");

                    System.out.println("-----------------------------------------------------------------------------------------------");

                    // Listar pedidos activos
                    pedidoRepo.listarActivos().forEach(pedido -> {

                        System.out.printf("%-5d %-12s %-15s %-18s %-25s $%-10.2f%n",
                                pedido.getId(),
                                pedido.getFecha(),
                                pedido.getEstado(),
                                pedido.getFormapago(),
                                pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido(),
                                pedido.getTotal());

                    });

                    break;
                }

                case 5: {

                    // Listar los usuarios activos
                    System.out.println("\n******** USUARIOS ACTIVOS ********");

                    usuarioRepo.listarActivos().forEach(usuario ->

                            System.out.printf("%-5d %-30s%n",
                                    usuario.getId(),
                                    usuario.getNombre() + " " + usuario.getApellido())

                    );

                    // Solicitar el ID del usuario
                    System.out.print("\nIngrese el ID del usuario: ");
                    Long idUsuario = scanner.nextLong();

                    // Buscar los pedidos del usuario
                    List<Pedido> pedidos = pedidoRepo.buscarPorUsuario(idUsuario);

                    // Verificar si tiene pedidos
                    if (pedidos.isEmpty()) {

                        System.out.println("El usuario no posee pedidos.");
                        break;

                    }

                    // Mostrar encabezado
                    System.out.println("\n******** PEDIDOS DEL USUARIO ********");

                    System.out.printf("%-5s %-12s %-15s %-10s%n",
                            "ID",
                            "FECHA",
                            "ESTADO",
                            "TOTAL");

                    System.out.println("-------------------------------------------------------");

                    // Mostrar los pedidos encontrados
                    pedidos.forEach(pedido ->

                            System.out.printf("%-5d %-12s %-15s $%-10.2f%n",
                                    pedido.getId(),
                                    pedido.getFecha(),
                                    pedido.getEstado(),
                                    pedido.getTotal())

                    );

                    break;
                }

                case 6: {

                    // Mostrar los estados disponibles
                    System.out.println("\nSeleccione un estado:");
                    System.out.println("1. PENDIENTE");
                    System.out.println("2. CONFIRMADO");
                    System.out.println("3. TERMINADO");
                    System.out.println("4. CANCELADO");

                    int opcionEstado = scanner.nextInt();

                    Estado estadoSeleccionado = Estado.PENDIENTE;

                    switch (opcionEstado) {

                        case 1:
                            estadoSeleccionado = Estado.PENDIENTE;
                            break;

                        case 2:
                            estadoSeleccionado = Estado.CONFIRMADO;
                            break;

                        case 3:
                            estadoSeleccionado = Estado.TERMINADO;
                            break;

                        case 4:
                            estadoSeleccionado = Estado.CANCELADO;
                            break;

                        default:
                            System.out.println("Estado inválido.");
                            break;
                    }

                    // Buscar los pedidos
                    List<Pedido> pedidos = pedidoRepo.buscarPorEstado(estadoSeleccionado);

                    if (pedidos.isEmpty()) {

                        System.out.println("No existen pedidos con ese estado.");
                        break;

                    }

                    // Mostrar resultados
                    System.out.println("\n******** PEDIDOS ********");

                    System.out.printf("%-5s %-12s %-25s %-10s%n",
                            "ID",
                            "FECHA",
                            "USUARIO",
                            "TOTAL");

                    System.out.println("-------------------------------------------------------------");

                    pedidos.forEach(pedido ->

                            System.out.printf("%-5d %-12s %-25s $%-10.2f%n",
                                    pedido.getId(),
                                    pedido.getFecha(),
                                    pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido(),
                                    pedido.getTotal())

                    );

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
    // MENU REPORTES
    private static void menuReportes(
            Scanner scanner,
            ProductoRepository productoRepo,
            CategoriaRepository categoriaRepo,
            UsuarioRepository usuarioRepo,
            PedidoRepository pedidoRepo) {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== REPORTES =====");
            System.out.println("1. Productos por categoría");
            System.out.println("2. Pedidos por usuario");
            System.out.println("3. Pedidos por estado");
            System.out.println("4. Total facturado");
            System.out.println("0. Volver");

            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1: {

                    // Listar categorías activas
                    List<Categoria> categorias = categoriaRepo.listarActivos();

                    if (categorias.isEmpty()) {

                        System.out.println("No existen categorías activas.");
                        break;

                    }

                    System.out.println("\n******** CATEGORÍAS ********");

                    categorias.forEach(categoria ->

                            System.out.printf("%-5d %-25s%n",
                                    categoria.getId(),
                                    categoria.getNombre())

                    );

                    // Solicitar la categoría
                    System.out.print("\nIngrese el ID de la categoría: ");
                    Long idCategoria = scanner.nextLong();

                    // Buscar productos
                    List<Producto> productos =
                            productoRepo.buscarPorCategoria(idCategoria);

                    if (productos.isEmpty()) {

                        System.out.println("No existen productos activos para esa categoría.");
                        break;

                    }

                    // Mostrar resultados
                    System.out.println("\n******** PRODUCTOS ********");

                    System.out.printf("%-5s %-25s %-12s %-10s%n",
                            "ID",
                            "NOMBRE",
                            "PRECIO",
                            "STOCK");

                    System.out.println("-----------------------------------------------------------");

                    productos.forEach(producto ->

                            System.out.printf("%-5d %-25s $%-10.2f %-10d%n",
                                    producto.getId(),
                                    producto.getNombre(),
                                    producto.getPrecio(),
                                    producto.getStock())

                    );

                    break;
                }

                case 2: {

                    // Listar usuarios activos
                    List<Usuario> usuarios = usuarioRepo.listarActivos();

                    if (usuarios.isEmpty()) {

                        System.out.println("No existen usuarios activos.");
                        break;

                    }

                    System.out.println("\n******** USUARIOS ********");

                    usuarios.forEach(usuario ->

                            System.out.printf("%-5d %-30s%n",
                                    usuario.getId(),
                                    usuario.getNombre() + " " + usuario.getApellido())

                    );

                    // Solicitar usuario
                    System.out.print("\nIngrese el ID del usuario: ");
                    Long idUsuario = scanner.nextLong();

                    // Buscar pedidos del usuario
                    List<Pedido> pedidos = pedidoRepo.buscarPorUsuario(idUsuario);

                    if (pedidos.isEmpty()) {

                        System.out.println("El usuario no posee pedidos.");
                        break;

                    }

                    // Mostrar resultados
                    System.out.println("\n******** PEDIDOS DEL USUARIO ********");

                    System.out.printf("%-5s %-12s %-15s %-18s %-10s%n",
                            "ID",
                            "FECHA",
                            "ESTADO",
                            "FORMA PAGO",
                            "TOTAL");

                    System.out.println("--------------------------------------------------------------------------");

                    pedidos.forEach(pedido ->

                            System.out.printf("%-5d %-12s %-15s %-18s $%-10.2f%n",
                                    pedido.getId(),
                                    pedido.getFecha(),
                                    pedido.getEstado(),
                                    pedido.getFormapago(),
                                    pedido.getTotal())

                    );

                    break;
                }

                case 3: {

                    // Mostrar los estados disponibles
                    System.out.println("\nSeleccione un estado:");
                    System.out.println("1. PENDIENTE");
                    System.out.println("2. CONFIRMADO");
                    System.out.println("3. TERMINADO");
                    System.out.println("4. CANCELADO");

                    int opcionEstado = scanner.nextInt();

                    Estado estadoSeleccionado = Estado.PENDIENTE;

                    switch (opcionEstado) {

                        case 1:
                            estadoSeleccionado = Estado.PENDIENTE;
                            break;

                        case 2:
                            estadoSeleccionado = Estado.CONFIRMADO;
                            break;

                        case 3:
                            estadoSeleccionado = Estado.TERMINADO;
                            break;

                        case 4:
                            estadoSeleccionado = Estado.CANCELADO;
                            break;

                        default:
                            System.out.println("Estado inválido.");
                            break;

                    }

                    // Buscar pedidos por estado
                    List<Pedido> pedidos = pedidoRepo.buscarPorEstado(estadoSeleccionado);

                    if (pedidos.isEmpty()) {

                        System.out.println("No existen pedidos con ese estado.");
                        break;

                    }

                    // Mostrar resultados
                    System.out.println("\n******** PEDIDOS ********");

                    System.out.printf("%-5s %-12s %-30s %-10s%n",
                            "ID",
                            "FECHA",
                            "USUARIO",
                            "TOTAL");

                    System.out.println("---------------------------------------------------------------------");

                    pedidos.forEach(pedido ->

                            System.out.printf("%-5d %-12s %-30s $%-10.2f%n",
                                    pedido.getId(),
                                    pedido.getFecha(),
                                    pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido(),
                                    pedido.getTotal())

                    );

                    break;
                }

                case 4: {

                    // Buscar todos los pedidos terminados
                    List<Pedido> pedidosTerminados =
                            pedidoRepo.buscarPorEstado(Estado.TERMINADO);

                    // Calcular el total facturado
                    double totalFacturado = pedidosTerminados.stream()

                            .map(Pedido::getTotal)
                            .filter(total -> total != null)
                            .mapToDouble(Double::doubleValue)
                            .sum();

                    // Mostrar resultado
                    System.out.println("\n******** TOTAL FACTURADO ********");

                    System.out.println("Total facturado: $" +
                            String.format(java.util.Locale.US, "%.2f", totalFacturado));

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

}