import p5 = require('p5')
import { WorldMap } from './types'

const worldMap = require('./copy_map.json') as WorldMap

export default (p: p5): void => {

    const textures = new Map<String, p5.Image>()


    p.preload = () => {
        const testenv: string[][] = [["kys", "asfalt"]]
        const missingTextures: string[] = []

        // load ze texturi
        for (let row of testenv) {
            for (let tileName of row) {


                if (!textures.has(tileName) && !missingTextures.includes(tileName)) {
                    try {
                        let img = p.loadImage(`/asset/${tileName}.png`)
                    } catch(e) {
                        console.log("XXXXXXXXXXXXX" + e)
                    }
                }

            }
        }

        console.log(missingTextures)
    }

    p.setup = () => {
        console.log("P5 RUNNING!")
        
        p.createCanvas(p.windowWidth - 30, p.windowHeight- 17)
        p.frameRate(120)
        
        
    }

    p.draw = () => {
        p.background(0)




        // draw cursor at top
        p.stroke("blue")
        p.strokeWeight(2)
        p.noFill()
        p.circle(p.mouseX, p.mouseY, 5)
    }

}