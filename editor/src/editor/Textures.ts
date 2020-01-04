import p5 = require("p5");
import { loadJSON, loadImg } from "./util";

export type TextureList = {
    textures: string[]
}

export const TEXPREFIX = "/assets/textures/"

export const entity = new Map<String, p5.Image>()
export const env = new Map<String, p5.Image>()
export const overlay = new Map<String, p5.Image>()
export const ui = new Map<String, p5.Image>()

export async function load(p: p5) {
    
    const entityList = await loadJSON<TextureList>(p, TEXPREFIX + "entityTextures.json")
    const envList = await loadJSON<TextureList>(p, TEXPREFIX + "envTextures.json")
    
    for (let texName of envList.textures) {
        env.set(texName, await loadImg(p, `/assets/textures/env/${texName}.png`))
    }
}