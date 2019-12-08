import p5 = require('p5')
const copy_map = require('./copy_map.json') as any

export default (p: p5): void => {

    p.setup = () => {
        console.log("P5 RUNNING!")
        console.log(copy_map)
        p.createCanvas(300, 300)
    }

    p.draw = () => {
        p.background(0)
    }

}