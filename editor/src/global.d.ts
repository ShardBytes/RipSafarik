import p5 from 'p5'

declare module "*.json"

// append sketch type to window interface
declare global {
    interface Window {
        s: p5
    }
}
