// Este servicio se encarga de manejar las peticiones relacionadas a los pedidos, como obtener la lista de pedidos, obtener un pedido por su ID, etc.
export async function getOrdersApi() {

    const response =
        await fetch('/data/pedidos.json')

    return await response.json()

}