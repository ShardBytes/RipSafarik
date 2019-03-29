package com.shardbytes.ripsafarik

/**
 * Value interpolation class.
 */
class Tween
/**
 * Constructs new interpolated number wrapper.
 * @param value Initial value
 * @param changeRate Rate at which the value should change
 * @param active Determines if this object is active or not
 * @param stopOnReached Determines if this object should remain active after the target value is reached
 */
(value: Double, private val changeRate: Double, private var active: Boolean, private val stopOnReached: Boolean) {

    /**
     * Returns current interpolated value as double.
     * @return Interpolated value
     */
    var double: Double = 0.toDouble()
        private set
    private var target: Double = 0.toDouble()

    /**
     * Returns current interpolated value as float.
     * @return Interpolated value
     */
    val float: Float
        get() = double.toFloat()

    /**
     * Returns current interpolated value as int.
     * @return Interpolated value
     */
    val int: Int
        get() = Math.round(double).toInt()

    /**
     * Returns current interpolated value as long.
     * @return Interpolated value
     */
    val long: Long
        get() = Math.round(double)

    init {
        this.double = value
        this.target = value
    }

    /**
     * Sets target value for this object.
     * @param value Target value
     */
    fun setTarget(value: Double) {
        this.target = value
    }

    /**
     * Sets defined value immediately without slowly interpolating to it.
     * @param value Defined value
     */
    fun setValue(value: Double) {
        this.target = value
        this.double = value
    }

    /**
     * Sets if this object is active and if its value should be interpolated.
     * @param active Should be active?
     */
    fun setActive(active: Boolean) {
        this.active = active
    }

    /**
     * Step the interpolation process by one step. This should be called every frame before
     * rendering (during input detection phase, ...).
     * @param delta Render time difference
     */
    fun step(delta: Float) {
        if (active) {
            val d = changeRate * delta

            if (double > target - d && double < target + d) {
                double = target

                if (stopOnReached) {
                    setActive(false)
                }

            }

            if (double < target) {
                double += d
            } else if (double > target) {
                double -= d
            }

        }

    }

}