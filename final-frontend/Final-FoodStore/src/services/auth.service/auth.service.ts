import { getUsersApi } from "../api/user.service"


const USER_KEY = 'user'
const USERS_KEY = 'users'


export function register(user:any){

    const users = getUsers()

    users.push(user)

    localStorage.setItem(
        USERS_KEY,
        JSON.stringify(users)
    )

}


export function getUsers(){

    const users = localStorage.getItem(USERS_KEY)

    return users ? JSON.parse(users) : []

}


export async function login(
    email:string,
    password:string
){

    // Usuarios del JSON
    const usersApi =
        await getUsersApi()

    // Usuarios registrados desde Register
    const usersLocal =
        getUsers()

    // Unimos ambos
    const users = [
        ...usersApi,
        ...usersLocal
    ]

    const userFound =
        users.find(
            (user:any) =>

                (
                    user.mail === email ||
                    user.email === email
                ) &&

                user.password === password
        )

    if(userFound){

        localStorage.setItem(
            USER_KEY,
            JSON.stringify(userFound)
        )

        return true

    }

    return false

}


export function getUser(){

    const user = localStorage.getItem(USER_KEY)

    return user ? JSON.parse(user) : null

}

export function logout(){

    localStorage.removeItem('user')

}