# Sistema de Gestión Comercial - Backend

## Descripción

Este proyecto corresponde al **Backend** del Trabajo Práctico Integrador de la materia **Programación III**.

La aplicación fue desarrollada en **Java** utilizando **JPA** e **Hibernate** para la persistencia de datos, implementando una arquitectura basada en el patrón **Repository**.

El sistema permite administrar categorías, productos, usuarios y pedidos, además de generar distintos reportes sobre la información almacenada.

---

## Tecnologías Utilizadas

- Java
- JPA (Jakarta Persistence API)
- Hibernate ORM
- H2 Database
- Lombok
- Gradle

---

## Funcionalidades

### Gestión de Categorías

- Alta
- Modificación
- Baja lógica
- Listado de categorías activas

### Gestión de Productos

- Alta
- Modificación
- Baja lógica
- Listado de productos
- Asociación con categorías
- Control de stock
- Control de disponibilidad

### Gestión de Usuarios

- Alta
- Modificación
- Baja lógica
- Listado de usuarios
- Búsqueda por correo electrónico

### Gestión de Pedidos

- Creación de pedidos
- Modificación de estado
- Baja lógica
- Consulta de pedidos
- Búsqueda por usuario
- Búsqueda por estado
- Descuento automático de stock

### Reportes

- Productos por categoría
- Pedidos por usuario
- Pedidos por estado
- Total facturado

---

## Arquitectura

El proyecto fue organizado utilizando una arquitectura en capas.

```
com.tu.programacion3
│
├── entities
├── repository
├── enums
├── interfaces
├── utils
├── temp
└── Main.java
```

### Entities

Contiene las entidades persistentes del sistema:

- Base
- Categoria
- Producto
- Usuario
- Pedido
- DetallePedido

### Repository

Implementa el acceso a la base de datos utilizando el patrón Repository.

Repositorios implementados:

- BaseRepository
- CategoriaRepository
- ProductoRepository
- UsuarioRepository
- PedidoRepository

---

## Persistencia

La persistencia fue implementada utilizando:

- EntityManager
- JPA
- Hibernate

Las operaciones principales utilizan transacciones mediante:

- begin()
- commit()
- rollback()

---

## Base de Datos

Motor utilizado:

- H2 Database

La persistencia se realiza mediante archivos locales, permitiendo conservar la información entre distintas ejecuciones.

---

## Relaciones implementadas

- Una Categoría posee muchos Productos.
- Un Usuario posee muchos Pedidos.
- Un Pedido posee muchos Detalles de Pedido.
- Un Producto puede pertenecer a muchos Detalles de Pedido.

---

## Principales características

- Arquitectura orientada a objetos.
- Patrón Repository.
- Persistencia mediante JPA/Hibernate.
- Uso de EntityManager.
- Baja lógica.
- Consultas JPQL.
- Transacciones.
- Control automático de stock.
- Cálculo automático del total del pedido.
- Uso de Enumeraciones.
- Uso de Lombok para reducir código repetitivo.

---

## Cómo ejecutar el proyecto

1. Clonar el repositorio.

```bash
git clone https://github.com/leonelthb/Trabajo-Integrador-Programacion.git
```

2. Abrir el proyecto con IntelliJ IDEA.

3. Esperar que Gradle descargue las dependencias.

4. Ejecutar la clase:

```
Main.java
```

5. La base de datos H2 será creada automáticamente.

---

## Integrantes

- Leonel Jesús Aballay

---

## Materia

Programación III

Tecnicatura Universitaria en Programación

Universidad Tecnológica Nacional