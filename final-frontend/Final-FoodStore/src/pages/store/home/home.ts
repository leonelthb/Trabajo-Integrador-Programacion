import './home.css'
import homeTemplate from './home.html?raw'
import { showToast } from '../../../utils/toast'
import { renderNavbar } from '../../../components/navbar/navbar'
import { addToCart } from '../../../services/cart.service/cart.service'
import { getUser } from '../../../services/auth.service/auth.service'

import { getCategories } from '../../../services/api/category.service'
import {    getAllProducts } from '../../../services/product-storage.service'

export async function renderHome(){

    const app =
        document.querySelector<HTMLDivElement>('#app')

    if(!app) return

    app.innerHTML = `
        ${renderNavbar()}
        ${homeTemplate}
    `

    const productos = await getAllProducts()
    const categorias = await getCategories()

    const container =
        document.querySelector<HTMLDivElement>(
            '#productos-container'
        )

    function renderProducts(productosFiltrados:any[]){

        if(!container) return

        const user = getUser()

        container.innerHTML = ''

        productosFiltrados.forEach(
            (producto:any, index:number) => {

                container.innerHTML += `

                    <div class="card">

                        <img
                            src="${producto.imagen}"
                            alt="${producto.nombre}"
                            class="product-image"
                        />

                        <h3>${producto.nombre}</h3>

                        <p>
                            Precio: $${producto.precio}
                        </p>

                        ${
                            user?.rol !== 'ADMIN'
                            ? `
                                <button id="btn-${index}">
                                    Agregar
                                </button>
                              `
                            : ''
                        }

                    </div>

                `
            }
        )

        productosFiltrados.forEach(
            (producto:any, index:number) => {

                const button =
                    document.querySelector(
                        `#btn-${index}`
                    )

                button?.addEventListener(
                    'click',
                    () => {

                        addToCart(producto)

                        showToast(
                            'Producto agregado al carrito'
                        )

                    }
                )

            }
        )

    }

    const categoriesContainer =
        document.querySelector(
            '#categories-container'
        )

    if(categoriesContainer){

        categoriesContainer.innerHTML = `

            <div class="category-item">
                Todas
            </div>

        `

        categorias.forEach((categoria:any) => {

            categoriesContainer.innerHTML += `

                <div
                    class="category-item"
                    data-id="${categoria.id}"
                >
                    ${categoria.nombre}
                </div>

            `

        })

    }

    setTimeout(() => {

        const categoryItems =
            document.querySelectorAll(
                '.category-item'
            )

        categoryItems.forEach(item => {

            item.addEventListener(
                'click',
                () => {

                    document
                        .querySelectorAll(
                            '.category-item'
                        )
                        .forEach(el =>
                            el.classList.remove(
                                'active'
                            )
                        )

                    item.classList.add(
                        'active'
                    )

                    const categoryName =
                        item.textContent?.trim()

                    if(
                        categoryName === 'Todas'
                    ){

                        renderProducts(
                            productos
                        )

                        return

                    }

                    const filteredProducts =
                        productos.filter(
                            (producto:any) =>
                                producto.categoria
                                    .nombre ===
                                categoryName
                        )

                    renderProducts(
                        filteredProducts
                    )

                }
            )

        })

        renderProducts(productos)

    }, 0)

    const searchInput =
        document.querySelector<HTMLInputElement>(
            '#search-input'
        )
        const sortSelect =
    document.querySelector<HTMLSelectElement>(
        '#sort-select'
    )

sortSelect?.addEventListener('change', () => {

    let productosOrdenados =
        [...productos]

    switch(sortSelect.value){

        case 'nombre-asc':

            productosOrdenados.sort(
                (a:any, b:any) =>
                    a.nombre.localeCompare(
                        b.nombre
                    )
            )

            break

        case 'nombre-desc':

            productosOrdenados.sort(
                (a:any, b:any) =>
                    b.nombre.localeCompare(
                        a.nombre
                    )
            )

            break

        case 'precio-asc':

            productosOrdenados.sort(
                (a:any, b:any) =>
                    a.precio - b.precio
            )

            break

        case 'precio-desc':

            productosOrdenados.sort(
                (a:any, b:any) =>
                    b.precio - a.precio
            )

            break

    }

    renderProducts(
        productosOrdenados
    )

})

    searchInput?.addEventListener(
        'input',
        () => {

            const value =
                searchInput.value
                    .toLowerCase()

            const filteredProducts =
                productos.filter(
                    (producto:any) =>

                        producto.nombre
                            .toLowerCase()
                            .includes(value)

                )

            renderProducts(
                filteredProducts
            )

        }
    )

}