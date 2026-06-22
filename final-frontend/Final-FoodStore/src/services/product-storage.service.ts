import { getProducts } from './api/product.service'

const PRODUCTS_STORAGE_KEY = 'admin_products'


// Obtiene productos del localStorage
export function getStoredProducts() {

    const products =
        localStorage.getItem(
            PRODUCTS_STORAGE_KEY
        )

    return products
        ? JSON.parse(products)
        : null

}

// Guarda productos en localStorage
export function saveProducts(
    products:any[]
) {

    localStorage.setItem(
        PRODUCTS_STORAGE_KEY,
        JSON.stringify(products)
    )

}



// Obtiene los productos que debe usar toda la aplicación
export async function getAllProducts() {

    const storedProducts =
        getStoredProducts()

    if(storedProducts){

        return storedProducts

    }

    return await getProducts()

}