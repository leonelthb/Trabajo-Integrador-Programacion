import { renderOrderDetail } from '../pages/client/order-detail/order-detail'
import { renderHome } from '../pages/store/home/home'
import { renderCart } from '../pages/store/cart/cart'
import { renderLogin } from '../pages/auth/login/login'
import { renderRegister } from '../pages/auth/register/register'
import { renderDashboard } from '../pages/admin/dashboard/dashboard'
import { isAdmin } from '../utils/auth.guard'
import { renderOrders } from '../pages/client/orders/orders'
import { renderAdminOrders } from '../pages/admin/orders/orders'
export function router(){

    const hash = window.location.hash

    //verificamos si estamos en una ruta de detalle

    if(hash.startsWith('#/order-detail/')){
        renderOrderDetail()
        return
    }

    switch(hash){

        case '#/cart':

            if(isAdmin()){

                alert(
                    'Los administradores no pueden realizar compras'
                )

                window.location.hash = '#/dashboard'

                break
            }

            renderCart()

            break

        case '#/home':
            renderHome()
            break

        case '#/login':
            renderLogin()
            break

        case '#/orders':
            renderOrders()
            break

        case '#/order-detail':
            renderOrderDetail()
            break

        case '#/register':
            renderRegister()
            break



        case '#/dashboard':

            if(isAdmin()){

                renderDashboard()

            }else{

                alert('Acceso denegado')

                window.location.hash = '#/home'

            }

            break

            case '#/admin-orders':

            if(isAdmin()){

                renderAdminOrders()

            }else{

                alert('Acceso denegado')

                window.location.hash = '#/home'

            }

            break

            default:
                renderHome()
        }

    }

// Este archivo se encarga de manejar las rutas de la aplicación. Dependiendo del hash en la URL, se carga el componente correspondiente (home o cart).
