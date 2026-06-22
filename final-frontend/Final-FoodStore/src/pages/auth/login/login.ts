import './login.css'
import loginTemplate from './login.html?raw'

import { renderNavbar } from '../../../components/navbar/navbar'

import { login } from '../../../services/auth.service/auth.service'

export function renderLogin(){

    const app = document.querySelector<HTMLDivElement>('#app')

    if(app){

        app.innerHTML = `
        
            ${renderNavbar()}
            ${loginTemplate}
        
        `

        const form = document.querySelector<HTMLFormElement>('#login-form')

        form?.addEventListener('submit', async (e) => {

            e.preventDefault()

            const emailInput = document.querySelector<HTMLInputElement>('#email')

            const passwordInput = document.querySelector<HTMLInputElement>('#password')

            const success = await login(
                emailInput!.value,
                passwordInput!.value
            )
        
if(success){

    alert('Login correcto')

    const user =
        JSON.parse(
            localStorage.getItem('user') || '{}'
        )

    if(user.rol === 'ADMIN'){

        window.location.hash =
            '#/dashboard'

    }else{

        window.location.hash =
            '#/home'

    }

}else{

    alert('Credenciales incorrectas')

}

        })

    }

}
