import p5 = require("p5")


export const loadJSON = <T>(ps: p5, url: string) => new Promise<T>((resolve, reject) => {
    ps.loadJSON(url, (obj: T) => {
        resolve(obj)
    }, (e: Event) => {
        reject("error loading JSON: " + url)
    })
})

export const loadImg = (ps: p5, imgurl: string) => new Promise<p5.Image>((resolve, reject) => {
    ps.loadImage(imgurl, (img) => {
        resolve(img)
    }, (e: Event) => {
        reject("error loading image: " + imgurl)
    })
})