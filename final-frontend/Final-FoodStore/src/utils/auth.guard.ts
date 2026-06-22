import { getUser } from '../services/auth.service/auth.service'

export function isAdmin(){
    const user = getUser()
    return user && user.rol === 'ADMIN'
}