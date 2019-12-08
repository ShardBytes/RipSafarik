import p5 = require('p5')
import { WorldMap } from './types'
const worldMap = require('./copy_map.json') as WorldMap

export default (p: p5): void => {

    let img: p5.Image

    p.setup = () => {
        console.log("P5 RUNNING!")

        p.createCanvas(p.windowWidth - 30, p.windowHeight- 17)

        img = p.loadImage("assets/kozmonaut.png")
        p.frameRate(120)
    }

    p.draw = () => {
        p.background(0)
        

        p.image(img, p.mouseX - img.width/2, p.mouseY - img.height/2)


        // draw cursor at top
        p.stroke("blue")
        p.strokeWeight(2)
        p.noFill()
        p.circle(p.mouseX, p.mouseY, 5)
    }

}