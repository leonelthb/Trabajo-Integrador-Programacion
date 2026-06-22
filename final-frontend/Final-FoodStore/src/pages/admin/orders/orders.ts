import './orders.css'
import ordersTemplate from './orders.html?raw'

import { renderNavbar } from '../../../components/navbar/navbar'
import { getOrders } from '../../../services/order.service/order.service'

export function renderAdminOrders(){

    const app =
        document.querySelector<HTMLDivElement>('#app')

    if(!app) return

    app.innerHTML = `

        ${renderNavbar()}
        ${ordersTemplate}

    `

    console.log(
        'PEDIDOS LOCALSTORAGE:',
        getOrders()
    )

    // Render inicial
    renderOrdersCards()

    const filter =
        document.querySelector<HTMLSelectElement>(
            '#status-filter'
        )

    filter?.addEventListener('change', () => {

        renderOrdersCards(
            filter.value
        )

    })

}

function renderOrdersCards(
    estado:string = 'TODOS'
){

    const allOrders =
        getOrders()

    const orders =
        estado === 'TODOS'
            ? allOrders
            : allOrders.filter(
                (order:any) =>
                    order.estado === estado
            )

            // Más recientes primero
            orders.sort(
                (a:any, b:any) =>
                    new Date(b.fecha).getTime() -
                    new Date(a.fecha).getTime()
            )

    const container =
        document.querySelector(
            '#admin-orders-container'
        )

    if(!container) return

    container.innerHTML = ''

    orders.forEach((order:any) => {

        container.innerHTML += `

            <div class="admin-order-card">

                <h3>
                    Pedido #${order.id}
                </h3>

                <p>
                    Cliente:
                    ${order.usuario?.nombre || 'Sin nombre'}
                </p>

                <p>
                Estado:
                <select
                    class="status-select"
                    data-id="${order.id}"
                >
                    <option
                        value="PENDIENTE"
                        ${order.estado === 'PENDIENTE' ? 'selected' : ''}
                    >
                        Pendiente
                    </option>

                    <option
                        value="EN_PREPARACION"
                        ${order.estado === 'EN_PREPARACION' ? 'selected' : ''}
                    >
                        En preparación
                    </option>

                    <option
                        value="ENTREGADO"
                        ${order.estado === 'ENTREGADO' ? 'selected' : ''}
                    >
                        Entregado
                    </option>

                </select>
            </p>

                <p>
                    Fecha:
                    ${new Date(order.fecha)
                        .toLocaleDateString()}
                </p>

                <p>
                    Productos:
                    ${order.detalles.length}
                </p>

                <p class="total">

                    Total:
                    $${order.total}

                </p>

                <button
                    class="detail-btn"
                    data-id="${order.id}">

                    Ver detalle

                </button>

            </div>

        `
    })

    // Cambiar estado del pedido
document
    .querySelectorAll('.status-select')
    .forEach(select => {

        select.addEventListener('change', () => {

            const id =
                select.getAttribute('data-id')

            const nuevoEstado =
                (select as HTMLSelectElement).value

            console.log(
                'CAMBIANDO ESTADO',
                id,
                nuevoEstado
            )

            const orders =
                getOrders()

            const pedido =
                orders.find(
                    (o:any) => o.id == id
                )

            if(pedido){

                pedido.estado =
                    nuevoEstado

                localStorage.setItem(
                    'orders',
                    JSON.stringify(orders)
                )

                console.log(
                    'ESTADO GUARDADO'
                )

            }

        })

    })

    // Evento para ver detalle
    document
        .querySelectorAll('.detail-btn')
        .forEach(button => {

            button.addEventListener('click', () => {

                const id =
                    button.getAttribute('data-id')

                window.location.hash =
                    `#/order-detail/${id}`

            })

        })

}