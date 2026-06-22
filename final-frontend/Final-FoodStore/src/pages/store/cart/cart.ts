import './cart.css'
import cartTemplate from './cart.html?raw'

import type { Product } from '../../../types/product'

import { showToast } from '../../../utils/toast'

import { renderNavbar } from '../../../components/navbar/navbar'

import {
    createOrderFromCart
} from '../../../services/order.service/order.service'

import {
    getCart,
    removeFromCart,
    increaseQuantity,
    decreaseQuantity,
    clearCart
} from '../../../services/cart.service/cart.service'

export function renderCart(){

    const app = document.querySelector<HTMLDivElement>('#app')

    if(!app) return

    app.innerHTML = `
        ${renderNavbar()}
        ${cartTemplate}
    `

    const cart = getCart()

    // Carrito vacío
    if(cart.length === 0){

        const container =
            document.querySelector<HTMLDivElement>('#cart-container')

        if(container){

            container.innerHTML = `

                <div class="empty-cart">

                    <h2>
                        🛒 Carrito vacío
                    </h2>

                    <p>
                        Agregá productos desde la tienda.
                    </p>

                    <a href="#/home">
                        Ir a comprar
                    </a>

                </div>

            `
        }

        return
    }

    const total = cart.reduce(
        (acc:number, producto:any) =>
            acc + (producto.precio * producto.cantidad),
        0
    )

    const container =
        document.querySelector<HTMLDivElement>('#cart-container')

    if(container){

        // Render productos
        cart.forEach((producto:any, index:number) => {

            container.innerHTML += `

                <div class="cart-item">

    <img
        src="/imagenes/${producto.imagen}"
        alt="${producto.nombre}"
        class="cart-image"
    />

                <div class="cart-info">

                    <h3>
                        ${producto.nombre}
                    </h3>

                    <p>
                        Precio: $${producto.precio}
                    </p>

                    <div class="quantity-controls">

                        <button
                            class="minus-btn"
                            data-index="${index}">
                            -
                        </button>

                        <span>
                            ${producto.cantidad}
                        </span>

                        <button
                            class="plus-btn"
                            data-index="${index}">
                            +
                        </button>

                    </div>

                    <p class="subtotal">
                        Subtotal:
                        $${producto.precio * producto.cantidad}
                    </p>

                    <button
                        class="remove-btn"
                        data-index="${index}">
                        Eliminar
                    </button>

                </div>

            </div>

            `
        })

        // Resumen
        const summaryContainer =
            document.querySelector('#cart-summary')

        if(summaryContainer){

            summaryContainer.innerHTML = `

                <div class="summary-card">

                    <h2>
                        Resumen del pedido
                    </h2>

                    <p>
                        Productos: ${cart.length}
                    </p>

                   <p class="summary-total">
                    Total: $${total}
                    </p>

                    <button id="checkout-btn">
                        Finalizar Compra
                    </button>

                    <button id="clear-cart-btn">
                        Vaciar carrito
                    </button>

                </div>

            `
        }
        
            const clearCartButton =
                document.querySelector<HTMLButtonElement>(
                    '#clear-cart-btn'
                )

            clearCartButton?.addEventListener('click', () => {

                const confirmDelete =
                    confirm('¿Vaciar todo el carrito?')

                if(confirmDelete){

                    clearCart()

                    renderCart()

                }

            })
            const checkoutButton =
            document.querySelector<HTMLButtonElement>(
                '#checkout-btn'
            )

            checkoutButton?.addEventListener('click', () => {

                if(cart.length === 0){

                    showToast('El carrito está vacío')

                    return

                }

                createOrderFromCart()

                showToast('Compra realizada con éxito 🎉')

                window.location.hash = '#/orders'

            })

        


        // Eliminar
        cart.forEach((_producto: Product, index:number) => {

            const button =
                document.querySelector<HTMLButtonElement>(
                    `.remove-btn[data-index="${index}"]`
                )

            button?.addEventListener('click', () => {

                removeFromCart(index)

                renderCart()

            })

        })

    }

    // Botones +

    const plusButtons =
        document.querySelectorAll('.plus-btn')

    plusButtons.forEach((button, index) => {

        button.addEventListener('click', () => {

            increaseQuantity(index)

            renderCart()

        })

    })

    // Botones -

    const minusButtons =
        document.querySelectorAll('.minus-btn')

    minusButtons.forEach((button, index) => {

        button.addEventListener('click', () => {

            decreaseQuantity(index)

            renderCart()

        })

    })

}