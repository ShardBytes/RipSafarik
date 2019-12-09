
export type WorldMap = {
    env: string[][]
    overlay: OverlayEntity[],
    entities: WorldEntity[]
}

export type OverlayEntity = {
    name: string,
    posX: number,
    posY: number,
    scale: number,
    rotation: number
}

export type WorldEntity = {
    className: string
}