import './order-detail.css'
import orderDetailTemplate from './order-detail.html?raw'
import { getOrderById } from '../../../services/order.service/order.service'
import { renderNavbar } from '../../../components/navbar/navbar'

export function renderOrderDetail(){

    const app =
        document.querySelector<HTMLDivElement>('#app')

    if(!app) return

    app.innerHTML = `
    
        ${renderNavbar()}
        ${orderDetailTemplate}
    
    `

    // obtenemos el hash completo
    const hash = window.location.hash

    const parts = hash.split('/')

    console.log(parts)

    const id = (
        parts[parts.length - 1]
    )

    console.log('ID del pedido:', id)

    const order = getOrderById(id)

    console.log('Detalle del pedido:', order)

    // contenedor donde mostraremos el detalle
const container =
    document.querySelector(
        '#order-detail-container'
    )

if(!container || !order) return

container.innerHTML = `

    <div class="order-info">

        <h2>
            Pedido #${order.id}
        </h2>

        <p>
            Fecha: ${new Date(order.fecha)
                .toLocaleDateString()}
        </p>

        <p>
            Estado: ${order.estado}
        </p>

        <p class="order-total">
            Total: $${order.total}
        </p>

    </div>

`

order.detalles.forEach(
    (detalle:any) => {

        container.innerHTML += `

            <div class="detail-card">

                <h3>
                    ${detalle.nombre}
                </h3>

                <p>
                    Cantidad:
                    ${detalle.cantidad}
                </p>

                <p>
                    Precio:
                    $${detalle.precio}
                </p>

                <p>
                    Subtotal:
                    $${detalle.precio * detalle.cantidad}
                </p>

            </div>

        `

    }
)

// Botón volver
const backButton =
    document.querySelector('#back-orders-btn')

backButton?.addEventListener('click', () => {

    window.location.hash = '#/orders'

})

}