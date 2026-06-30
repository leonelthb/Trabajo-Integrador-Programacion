# 🍔 FoodStore - Frontend

## Descripción

FoodStore es una aplicación web desarrollada con TypeScript, HTML, CSS y Vite que simula el funcionamiento de un sistema de pedidos para un local de comidas.

La aplicación consume información desde archivos JSON y utiliza LocalStorage para almacenar la información generada por los usuarios, permitiendo simular el comportamiento de una aplicación conectada a un backend.

---

# Tecnologías utilizadas

- TypeScript
- HTML5
- CSS3
- Vite
- Fetch API
- LocalStorage

---

# Instalación

Clonar el repositorio:

```bash
git clone <url-del-repositorio>
```

Ingresar a la carpeta:

```bash
cd FoodStore
```

Instalar dependencias:

```bash
npm install
```

Ejecutar el proyecto:

```bash
npm run dev
```

Abrir en el navegador:

```
http://localhost:5173
```

---

# Funcionalidades

## Autenticación

- Inicio de sesión.
- Registro de usuarios.
- Validación de credenciales.
- Persistencia de sesión mediante LocalStorage.
- Redirección según el rol del usuario.

---

## Cliente

- Visualización del catálogo de productos.
- Búsqueda de productos por nombre.
- Filtrado por categorías.
- Ordenamiento por:
  - Nombre A-Z.
  - Nombre Z-A.
  - Precio de menor a mayor.
  - Precio de mayor a menor.
- Agregar productos al carrito.
- Modificar cantidades.
- Eliminar productos del carrito.
- Confirmar pedidos.
- Historial de pedidos del usuario.
- Visualización del detalle de cada pedido.

---

## Administrador

- Dashboard con estadísticas.
- CRUD de productos.
- Gestión de pedidos.
- Visualización del detalle de los pedidos.
- Cambio de estado de los pedidos.
- Filtro de pedidos por estado.
- Visualización del nombre del cliente.
- Ordenamiento de pedidos por fecha.

---

# Persistencia de datos

La aplicación utiliza dos mecanismos de almacenamiento:

- Archivos JSON para cargar la información inicial.
- LocalStorage para almacenar:
  - Usuario autenticado.
  - Carrito de compras.
  - Productos administrados.
  - Pedidos realizados.

---

# Roles

## Administrador

Puede:

- Administrar productos.
- Gestionar pedidos.
- Cambiar el estado de los pedidos.
- Acceder al Dashboard.

## Usuario

Puede:

- Navegar por el catálogo.
- Buscar y filtrar productos.
- Agregar productos al carrito.
- Realizar pedidos.
- Consultar el historial de compras.
- Visualizar el detalle de sus pedidos.

---

# Estructura del proyecto

```
src/
├── components/
├── pages/
├── services/
├── router/
├── utils/
└── main.ts

public/
├── data/
│   ├── categorias.json
│   ├── productos.json
│   ├── usuarios.json
│   └── pedidos.json
└── imagenes/
```

---

# Observaciones

Este proyecto corresponde al desarrollo del Frontend del Trabajo Integrador de Programación III.

En esta etapa se trabajó utilizando archivos JSON y LocalStorage para simular la persistencia de datos. La arquitectura fue desarrollada de forma modular para facilitar su integración con un backend en futuras etapas del proyecto.

---

Clonar el repositorio.

```bash
git clone https://github.com/leonelthb/Trabajo-Integrador-Programacion.git
```


# Autor

**Leonel Jesús Aballay**

Tecnicatura Universitaria en Programación

Universidad Tecnológica Nacional (UTN)