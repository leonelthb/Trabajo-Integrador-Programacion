import './navbar.css'
import { logout } from '../../services/auth.service/auth.service'
import { getUser } from '../../services/auth.service/auth.service'
import { getCart } from '../../services/cart.service/cart.service' 
export function renderNavbar(){

    const cart = getCart()
    
    const totalItems = cart.reduce(
    (acc: number, producto: any) =>
        acc + producto.cantidad,
    0
    )

    const user = getUser()

    let links = `
    
        <li>
            <a href="#/home">Home</a>
        </li>
    
    `

if(user){

    if(user.rol === 'ADMIN'){

        links += `
        
            <li>
                <a href="#/dashboard">
                    Panel Admin
                </a>
            </li>

            <li>
                <a href="#/admin-orders">
                    Pedidos
                </a>
            </li>
        
        `

    }else{

        links += `
        
            <li>
                <a href="#/cart" class="cart-link">
                    🛒 Carrito
                    <span class="cart-badge">
                        ${totalItems}
                    </span>
                </a>
            </li>
        
        `
    }

    links += `
    
        <li>
            <a href="#" id="logout-btn">
                Logout
            </a>
        </li>
    
    `

}else{

    links += `
    
        <li>
            <a href="#/login">
                Login
            </a>
        </li>

        <li>
            <a href="#/register">
                Register
            </a>
        </li>
    
    `
}
    setTimeout(() => {

    const logoutBtn = document.querySelector('#logout-btn')

    logoutBtn?.addEventListener('click', () => {

        logout()

        window.location.hash = '#/login'

    })

}, 0)

    return `
    
        <nav class="navbar">

            <h2 class="logo">
                FoodStore
            </h2>

            <ul class="nav-links">

                ${links}

            </ul>

        </nav>
    
    `
}