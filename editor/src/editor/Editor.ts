import p5 = require('p5')
import { WorldMap } from './types'
import Textures = require('./Textures')

const copy_map = require('../copy_map.json') as WorldMap
const minimap = require("../minimap.json") as WorldMap


export default (p: p5): void => {

    const worldMap = copy_map

    // loading
    let loadState = 0
    let loadLog = ""
    let renderer: p5.Renderer

    // map setup
    const textures = new Map<String, p5.Image>()
    let tsize = 100  // tile size

    // custom loader, probably needs rework and doesn't need promises but if it isnt causing trouble then why not its cool
    const loader = async () => {
        await Textures.load(p)
    }

    p.setup = () => {
        console.log("P5 RUNNING!")

        // activate loader and handle load results
        loader().then(() => {
            console.log("[Loader] All loaded succesfully")
            loadState = 2
        }).catch((e: any) => {
            console.log("[Loader] Error loading: " + e)
            loadState = 1
        })

        renderer = p.createCanvas(p.windowWidth - 20, p.windowHeight - 20)
        // disable image smoothing
        let context = renderer.elt.getContext('2d');
        context.mozImageSmoothingEnabled = false;
        context.webkitImageSmoothingEnabled = false;
        context.msImageSmoothingEnabled = false;
        context.imageSmoothingEnabled = false;

        p.pixelDensity(1) // fix pixel density
        p.frameRate(60)
        p.textSize(20)
        p.textStyle("bold")
        
        
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
                p.image( Textures.env.get(worldMap.env[yi][xi])!! , xi*tsize, yi*tsize, tsize, tsize)
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
                tsize += 10
                break
            case "y":
                tsize -= 10
                break
        }
    }

    p.mouseWheel = (e: {delta: number}) => {
        if (e.delta > 0) {
            tsize -= 10
        } else {
            tsize += 10
        }
    }

}