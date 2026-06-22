import './register.css'
import registerTemplate from './register.html?raw'

import { renderNavbar } from '../../../components/navbar/navbar'

import { register } from '../../../services/auth.service/auth.service'

export function renderRegister(){

    const app = document.querySelector<HTMLDivElement>('#app')

    if(app){

        app.innerHTML = `
        
            ${renderNavbar()}
            ${registerTemplate}
        
        `

        const form = document.querySelector<HTMLFormElement>('#register-form')

        form?.addEventListener('submit', (e) => {

            e.preventDefault()

            const nameInput = document.querySelector<HTMLInputElement>('#name')
            const emailInput = document.querySelector<HTMLInputElement>('#email')
            const passwordInput = document.querySelector<HTMLInputElement>('#password')
            const rolInput = document.querySelector<HTMLSelectElement>('#rol')

            const user = {

                nombre: nameInput!.value,
                email: emailInput!.value,
                password: passwordInput!.value,
                rol: rolInput!.value

            }

            register(user)

            alert('Usuario registrado')

            window.location.hash = '#/login'

        })

    }

}