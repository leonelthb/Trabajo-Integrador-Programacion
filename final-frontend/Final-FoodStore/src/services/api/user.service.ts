// Este servicio se encarga de manejar las peticiones relacionadas a los usuarios, como obtener la lista de usuarios, obtener un usuario por su ID, etc.
export async function getUsersApi() {

    const response =
        await fetch('/data/usuarios.json')

    const users =
        await response.json()

    console.log('USUARIOS API:', users)

    return users

}