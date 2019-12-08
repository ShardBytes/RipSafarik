/*
 * Editor for RipSafarik by @Plasmoxy
 */

import Editor from './Editor'
import axios from 'axios'
import p5 = require('p5')

// append sketch type to window interface
declare global {
    interface Window {
        s: p5
    }
}

// launch p5 sketch attached to window
window.s = new p5(Editor)

window.addEventListener("beforeunload", (ev) => {
    
})

axios.get("/exit").then()