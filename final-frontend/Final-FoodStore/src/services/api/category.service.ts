// obtiene las categorias desde el Json provisto
export async function getCategories() {

    const response =
        await fetch('/data/categorias.json')

    const categories =
        await response.json()

    return categories

}