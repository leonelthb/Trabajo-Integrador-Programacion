// Este servicio se encarga de manejar las peticiones relacionadas a los productos, como obtener la lista de productos, obtener un producto por su ID, etc.
export async function getProducts() {

    const response =
        await fetch('/data/productos.json')

    return await response.json()

}