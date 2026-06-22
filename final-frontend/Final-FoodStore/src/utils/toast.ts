export function showToast(message:string){

    const container =
        document.querySelector('#toast-container')

    if(!container) return

    const toast = document.createElement('div')

    toast.className = 'toast'

    toast.textContent = message

    container.appendChild(toast)

    setTimeout(() => {

        toast.remove()

    }, 3000)
}