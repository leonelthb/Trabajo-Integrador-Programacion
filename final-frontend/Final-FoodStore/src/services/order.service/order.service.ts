import { getCart, clearCart } from '../cart.service/cart.service';
import { getUser } from '../auth.service/auth.service';

const ORDERS_KEY = 'orders';

export function getOrders() {

    const orders =
        localStorage.getItem('orders')

    return orders
        ? JSON.parse(orders)
        : []

}

export function saveOrder(order:any) {

    const orders = getOrders()

    orders.push(order)

    localStorage.setItem(
        'orders',
        JSON.stringify(orders)
    )

}

export function createOrderFromCart(){
    const cart = getCart()
    // Usuario que está realizando la compra
    const user =
    getUser()

    if(cart.length === 0) return
    const total = cart.reduce(
        (acc: number, producto: any) => acc + (producto.precio * producto.cantidad
    ),
    0
)
        // Nuevo pedido
        const order = {

            id: Date.now().toString(),

            fecha: new Date().toISOString(),

            estado: 'PENDIENTE',

            total,

            // Usuario que realizó la compra
            usuario: {

                nombre: user.nombre,

                email: user.mail,

                rol: user.rol

            },

            detalles: cart

        }
    saveOrder(order)

    clearCart()
    return order
}

// Función para buscar un pedido por su id
export function getOrderById(id:string){
    const orders = getOrders()

        // buscamos dentro del arreglo de pedido cuyo id coincida con el recibo
    return orders.find(
        (order:any) => order.id === id
    )
}