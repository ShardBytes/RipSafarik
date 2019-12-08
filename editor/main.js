const express = require("express")
const getPort = require("get-port")
const open = require('open')

const STATIC_DIR = (process.argv[2] == "dev") ? "dist" : "build"

console.log("=== RipSafarik Editor ===")
let server

;(async () => {
    
    const app = express()
    app.use(express.static("dist"))

    app.get("/exit", (req, res) => {
        console.log("GOT EXIT REQUEST? CLOSING SERVER...")
        res.send("OK")
        server.close()
    })

    const port = await getPort()
    server = app.listen(port, () => {
        const addr = "http://localhost:" + port
        console.log("serving at " + addr)
        console.log("Opening in default browser! Close page to stop server...")
        open(addr)
    })
})().catch(e => console.log(e))