import p5 = require('p5')
import { WorldMap } from './types'
const worldMap = require('./copy_map.json') as WorldMap

export default (p: p5): void => {

    p.setup = () => {
        console.log("P5 RUNNING!")

        p.createCanvas(p.windowWidth - 30, p.windowHeight- 17)
        p.noCursor()

    }

    p.draw = () => {
        p.background(0)
        p.stroke("red")
        p.noFill()
        p.circle(p.mouseX, p.mouseY, 10)
    }

}