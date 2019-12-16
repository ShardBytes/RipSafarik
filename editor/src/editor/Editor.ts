import p5 = require('p5')
import { WorldMap } from './types'

const copy_map = require('../copy_map.json') as WorldMap
const minimap = require("../minimap.json") as WorldMap

export const loadImg = (ps: p5, imgurl: string) => new Promise<p5.Image>((resolve, reject) => {
    ps.loadImage(imgurl, (img) => {
        resolve(img)
    }, (e: Event) => {
        reject("error loading image: " + imgurl)
    })
})


export default (p: p5): void => {

    const worldMap = copy_map

    // loading
    let loadState = 0
    let loadLog = ""
    let renderer: p5.Renderer

    // map setup
    const textures = new Map<String, p5.Image>()
    let tileSize = 100  

    // custom loader, probably needs rework and doesn't need promises but if it isnt causing trouble then why not its cool
    const loader = async () => {
        const missingTextures: string[] = []
        let currentImage: p5.Image

        for (let row of worldMap.env) {
            for (let name of row) {
                try {
                    if ( !textures.has(name) && !missingTextures.includes(name)) {
                        currentImage = await loadImg(p, `/assets/textures/env/${name}.png`)
                        // ... resize or some shit idk
                        textures.set(name, currentImage)
                    }
                } catch {
                    console.log("Missing: " + name)
                    loadLog += "\nmissing texture: " + name
                    missingTextures.push(name)
                }
            }
        }

        if (missingTextures.length > 0) {
            throw "Missing some textures: " + missingTextures.toString()
        }
    }

    p.setup = () => {
        console.log("P5 RUNNING!")

        // activate loader and handle load results
        loader().then(() => {
            console.log("[Loader] All loaded succesfully")
            loadState = 2
        }).catch((s: any) => {
            console.log("[Loader] Error loading: " + s)
            loadState = 1
        })

        p.pixelDensity(1) // fix pixel density
        p.noSmooth()
        renderer = p.createCanvas(p.windowWidth - 30, p.windowHeight- 17)
        p.frameRate(60)
        p.textSize(20)
        p.textStyle("bold")

        // disable image smoothing
        let context = renderer.elt.getContext('2d');
        context.mozImageSmoothingEnabled = false;
        context.webkitImageSmoothingEnabled = false;
        context.msImageSmoothingEnabled = false;
        context.imageSmoothingEnabled = false;
    }

    p.draw = () => {
        
        // handle loader results
        if (loadState == 0) { // 0 loading...
            p.background(0)
            p.text("Loading ...", 20, 20)
            return
        } else if (loadState == 1) { // 1 error ...
            p.background(200, 20, 20)
            p.fill(0)
            p.text("Loader error ... " + loadLog, 20, 20)
            return
        }

        // 2 ok loaded ...

        // bg
        p.background(0)

        // try to draw worldmap environ
        for ( let yi = 0; yi < worldMap.env.length; yi++ ) {
            for ( let xi = 0; xi < worldMap.env[yi].length; xi++ ) {
                // manually draw by scale size
                p.image( textures.get(worldMap.env[yi][xi])!! , xi*tileSize, yi*tileSize, tileSize, tileSize)
            }
        }

        // draw cursor at top
        p.stroke(255, 255, 0)
        p.strokeWeight(2)
        p.noFill()
        p.circle(p.mouseX, p.mouseY, 5)
    }


    p.keyPressed = () => {
        switch (p.key) {
            case "x":
                tileSize += 10
                break
            case "y":
                tileSize -= 10
                break
        }
    }

}