import './dashboard.css'

import { getCategories } from '../../../services/api/category.service'
import { getProducts } from '../../../services/api/product.service'
import { getOrdersApi } from '../../../services/api/order.service'
import dashboardTemplate from './dashboard.html?raw'
import { renderNavbar } from '../../../components/navbar/navbar'
import {    getStoredProducts,    saveProducts } from '../../../services/product-storage.service'
export async function renderDashboard(){

    const app = document.querySelector<HTMLDivElement>('#app')
    // Obtiene categorías desde categorias.json
    const categorias =
        await getCategories()

    // Obtiene productos del JSON
    const apiProducts =
        await getProducts()

    // Si hay productos guardados por admin,
    // usamos esos. Si no, usamos el JSON.
    const productos =
        getStoredProducts()
        || apiProducts

    // Obtiene pedidos desde pedidos.json
    const pedidos =
        await getOrdersApi()

    // Cantidad de categorías
    const totalCategorias =
        categorias.length

    // Cantidad de productos
    const totalProductos =
        productos.length

    // Cantidad de pedidos
    const totalPedidos =
        pedidos.length

    // Productos con stock disponible
    const productosDisponibles =
        productos.filter(
            (producto:any) => producto.stock > 0
        ).length 


    // Botón para ver pedidos
    const viewOrdersButton =
    document.querySelector('#view-orders-btn')

viewOrdersButton?.addEventListener('click', () => {

    window.location.hash = '#/admin-orders'

})



if(app){

    app.innerHTML = `

        ${renderNavbar()}
        ${dashboardTemplate}

    `

    const statsContainer =
        document.querySelector('#stats-container')

    if(statsContainer){

        statsContainer.innerHTML = `

            <div class="stats-container">

                <div class="stat-card">
                    <h3>Categorías</h3>
                    <p>${totalCategorias}</p>
                </div>

                <div class="stat-card">
                    <h3>Productos</h3>
                    <p>${totalProductos}</p>
                </div>

                <div class="stat-card">
                    <h3>Pedidos</h3>
                    <p>${totalPedidos}</p>
                </div>

                <div class="stat-card">
                    <h3>Disponibles</h3>
                    <p>${productosDisponibles}</p>
                </div>

            </div>

        `
    }

    const formContainer =
    document.querySelector('#product-form-container')

    const newProductButton =
    document.querySelector('#new-product-btn')

    const container =
    document.querySelector('#admin-products-container')

if(container){

    productos.forEach((producto:any, index:number) => {

        container.innerHTML += `
-
            <div class="admin-card">

                <h3>
                    ${producto.nombre}
                </h3>

                <p>
                    Precio: $${producto.precio}
                </p>

                <p>
                    Stock: ${producto.stock}
                </p>

                <button
                    class="edit-btn"
                    data-index="${index}">
                    Editar
                </button>

                <button
                    class="delete-btn"
                    data-index="${index}">
                    Eliminar
                </button>

            </div>

        `
    })

    newProductButton?.addEventListener('click', () => {

    if(!formContainer) return
        formContainer.innerHTML = `

    <div class="product-form">

        <input
            type="text"
            id="product-name"
            placeholder="Nombre"
        >

        <input
            type="number"
            id="product-price"
            placeholder="Precio"
        >

        <!-- Selección de categoría -->
        <select id="product-category">

            <option value="Hamburguesas">
                Hamburguesas
            </option>

            <option value="Pizzas">
                Pizzas
            </option>

            <option value="Empanadas">
                Empanadas
            </option>

            <option value="Bebidas">
                Bebidas
            </option>

            <option value="Papas Fritas">
                Papas Fritas
            </option>

        </select>

        <input
            type="number"
            id="product-stock"
            placeholder="Stock"
        >

        <button id="save-product-btn">
            Guardar
        </button>

    </div>

`
setTimeout(() => {

    const saveButton =
        document.querySelector('#save-product-btn')

    saveButton?.addEventListener('click', () => {

        const nombre =
            (
                document.querySelector(
                    '#product-name'
                ) as HTMLInputElement
            ).value

        const precio =
            Number(
                (
                    document.querySelector(
                        '#product-price'
                    ) as HTMLInputElement
                ).value
            )

        const stock =
            Number(
                (
                    document.querySelector(
                        '#product-stock'
                    ) as HTMLInputElement
                ).value
            )
        // Obtiene la categoría seleccionada
        const categoriaNombre =
            (
                document.querySelector(
                    '#product-category'
                ) as HTMLSelectElement
            ).value
            productos.push({

                id: Date.now(),

                nombre,

                precio,

                stock,

                eliminado: false,

                createdAt: new Date().toISOString(),

                descripcion: '',

                imagen: '',

                disponible: true,

                // Categoría del producto
                categoria: {

                    id: Date.now(),

                    nombre: categoriaNombre,

                    descripcion: ''

                }

            })
        // Guardamos cambios
        saveProducts(productos)

        renderDashboard()

    })

}, 0)
    
})

    document
    .querySelectorAll('.delete-btn')
    .forEach((button, index) => {

        button.addEventListener('click', () => {

            const confirmDelete =
                confirm(
                    '¿Eliminar producto?'
                )

            if(confirmDelete){

                productos.splice(index, 1)
                // Guardamos cambios
                saveProducts(productos)

                renderDashboard()

            }

        })

    })

    document
    .querySelectorAll('.edit-btn')
    .forEach((button, index) => {

        button.addEventListener('click', () => {

            const producto = productos[index]

            if(!formContainer) return

            formContainer.innerHTML = `

                <div class="product-form">

                    <input
                        type="text"
                        id="product-name"
                        value="${producto.nombre}"
                    >

                    <input
                        type="number"
                        id="product-price"
                        value="${producto.precio}"
                    >

                    <input
                        type="number"
                        id="product-stock"
                        value="${producto.stock}"
                    >

                    <button id="update-product-btn">
                        Actualizar
                    </button>

                </div>

            `

            setTimeout(() => {

                const updateButton =
                    document.querySelector(
                        '#update-product-btn'
                    )

                updateButton?.addEventListener(
                    'click',
                    () => {

                        const nombre =
                            (
                                document.querySelector(
                                    '#product-name'
                                ) as HTMLInputElement
                            ).value

                        const precio =
                            Number(
                                (
                                    document.querySelector(
                                        '#product-price'
                                    ) as HTMLInputElement
                                ).value
                            )

                        const stock =
                            Number(
                                (
                                    document.querySelector(
                                        '#product-stock'
                                    ) as HTMLInputElement
                                ).value
                            )

                        productos[index].nombre =
                            nombre

                        productos[index].precio =
                            precio

                        productos[index].stock =
                            stock

                            // Guardamos cambios
                        saveProducts(productos)

                        renderDashboard()

                    }
                )

            }, 0)

        })

    })

}
}

}