import './styles/global.css'

import { router } from './routes/router'

window.addEventListener('load', router)

window.addEventListener('hashchange', router)