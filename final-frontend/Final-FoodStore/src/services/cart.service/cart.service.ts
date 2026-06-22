const CART_KEY = 'cart';

export function getCart() {
    const cart = localStorage.getItem(CART_KEY);
    return cart ? JSON.parse(cart) : []

}

export function addToCart(producto: any){

    const cart = getCart()

    const existingProduct = cart.find(
        (item: any) => item.id === producto.id
    )

    if(existingProduct){

        existingProduct.cantidad++

    }else{

        cart.push({
            ...producto,
            cantidad: 1
        })

    }

    localStorage.setItem(
        CART_KEY,
        JSON.stringify(cart)
    )

}

export function removeFromCart(index:number){
    const cart = getCart()

    cart.splice(index, 1)

    localStorage.setItem(
        CART_KEY,
        JSON.stringify(cart)
    )
}

export function increaseQuantity(index:number){

    const cart = getCart()

    cart[index].cantidad++
    localStorage.setItem(
        CART_KEY,
        JSON.stringify(cart)
    )
}

export function decreaseQuantity(index:number){

    const cart = getCart()

    cart[index].cantidad--
    if(cart[index].cantidad <= 0){
        cart.splice(index, 1)
    }
    localStorage.setItem(
        CART_KEY,
        JSON.stringify(cart)
    )
}

export function clearCart(){
    localStorage.removeItem(CART_KEY)
}
