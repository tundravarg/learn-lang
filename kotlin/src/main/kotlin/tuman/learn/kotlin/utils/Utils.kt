package tuman.learn.kotlin.utils


fun round(value: Double, scale: Int): Double {
    val k = Math.pow(10.0, scale.toDouble())
    return Math.round(value * k) / k
}
