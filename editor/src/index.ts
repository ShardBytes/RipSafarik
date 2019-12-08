/*
 * Editor for RipSafarik by @Plasmoxy
 */

import Editor from './Editor'
import axios from 'axios'
import p5 = require('p5')


let isRefresh = false

// launch p5 sketch attached to window
window.s = new p5(Editor)

window.addEventListener("keydown", (e) => {
    // intercept f5
    if (e.keyCode == 116) {
        isRefresh = true
    }
})

window.addEventListener("beforeunload", (ev) => {
    if (!isRefresh) {
        axios.get("/exit").then(() => {
            console.log("Exit req sent.")
        })
    }
})

