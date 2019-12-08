const express = require("express")
const getPort = require("get-port")
const open = require('open')

const STATIC_DIR = (process.argv[2] == "dev") ? "dist" : "build"

console.log("=== RipSafarik Editor ===")
let server

;(async () => {
    
    const app = express()
    app.use(express.static(STATIC_DIR))
    
    app.get("/asset/:assetname", (req, res) => {
        res.sendFile(__dirname + `/assets/${req.params.assetname}`)
    })

    app.get("/exit", (req, res) => {
        console.log("GOT EXIT REQUEST? CLOSING SERVER...")
        res.send("OK")
        server.close(() => process.exit(0))
    })

    const port = await getPort()
    server = app.listen(port, () => {
        const addr = "http://localhost:" + port
        console.log("serving at " + addr)
        console.log("Opening in default browser! Close page to stop server...")
        console.log("F5 doesn't close server, but refresh does!")
        open(addr)
    })
})().catch(e => console.log(e))