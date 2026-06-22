import './orders.css'
import ordersTemplate from './orders.html?raw'
import { renderNavbar } from '../../../components/navbar/navbar'
import { getOrders } from '../../../services/order.service/order.service'
import { getUser } from '../../../services/auth.service/auth.service'

export function renderOrders(){

    const app =
        document.querySelector<HTMLDivElement>('#app')

    if(!app) return

    app.innerHTML = `
    
        ${renderNavbar()}
        ${ordersTemplate}
    
    `

// Usuario logueado
const user =
    getUser()

// Todos los pedidos
const allOrders =
    getOrders()

// Solo pedidos del usuario actual
const orders =
    allOrders.filter(
        (order:any) =>
            order.usuario?.email === user.mail
    )

        console.log('USER:', user)
        console.log('ALL ORDERS:', allOrders)
        console.log('ORDERS FILTRADOS:', orders)

    const container =
        document.querySelector('#orders-container')

    if(!container) return

    if(orders.length === 0){

        container.innerHTML = `

            <div class="empty-orders">

                <h2>
                    No tienes pedidos todavía
                </h2>

            </div>

        `

        return
    }

    orders.forEach((order:any) => {

        const fecha =
            new Date(order.fecha)
                .toLocaleDateString()

        container.innerHTML += `

            <div class="order-card">

                <h3>
                    Pedido #${order.id}
                </h3>

                <p>
                    Fecha: ${fecha}
                </p>

                <p>
                    Estado: ${order.estado}
                </p>

                <p class="order-total">
                    Total: $${order.total}
                </p>

                
                <button
                    class="detail-btn"
                    data-id="${order.id}"
                >
                    Ver detalle
                </button>

            </div>

        `

    })

            // Recorremos todos los botones "Ver detalle"
        document
            .querySelectorAll('.detail-btn')
            .forEach(button => {

                // Escuchamos el click del botón
                button.addEventListener('click', () => {

                    // Obtenemos el id del pedido
                    const id =
                        button.getAttribute('data-id')

                    // Navegamos a la pantalla de detalle
                    window.location.hash =
                        `#/order-detail/${id}`

                })

            })

}