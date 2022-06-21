package tuman.learn.kotlin


import tuman.learn.kotlin.utils.test


fun learnKotlinClasses() {
//    learnBasicClasses()
    learnOperatorOverloading()
}


fun learnBasicClasses() {
    test("Basic Classes") {

        fun Point.print() {
            println("point: $this")
            println("x: ${this.x}")
            println("y: ${this.y}")
            println("distance: ${this.distance}")
        }

        println("-- Basic Point")
        var p = Point(5.0, 10.0)
        p.print()

        // Can't modify basic Point
//        p.distance = 10
//        p.x = 3.3
//        p.y = 5.5

        println("-- Editable Point")
        p = EditablePoint(p)
        p.print()

        println("-- Edited point")
        p.x = 3.3
        p.y = 5.5
        p.print()

        println("-- Distance has been changed")
        p.distance = 12.83
        p.print()

    }
}


open class Point(open val x: Double, open val y: Double) {

    constructor(p: Point): this(p.x, p.y)

    override fun toString(): String {
        return "{$x, $y}"
    }

    open val distance: Double
        get() = Math.sqrt(x * x + y * y)

}


class EditablePoint(override var x: Double, override var y: Double): Point(x, y) {

    constructor(p: Point): this(p.x, p.y)

    override var distance: Double
        get() = super.distance
        set(value) {
            val k = value / distance
            x *= k
            y *= k
        }

}


fun learnOperatorOverloading() {
    test("Operator Overloading") {
        var stdio = StdIO()
        (stdio + "1" + '2' + 3) * "a" + "b" + "c"  // 123 abc
        stdio++                                    // \n
        stdio / "asd"                              // asd\n
        stdio / "qwe"                              // qwe\n
        stdio / "zxc"                              // zxc\n
    }
}

class StdIO() {

    private var newLine = true

    operator fun plus(obj: Any): StdIO {
        print(obj)
        newLine = false
        return this
    }

    operator fun div(obj: Any): StdIO {
        println(obj)
        newLine = true
        return this
    }

    operator fun times(obj: Any): StdIO {
        if (!newLine) {
            print(" ")
        }
        print(obj)
        newLine = false
        return this
    }

    operator fun inc(): StdIO {
        println()
        newLine = true
        return this
    }

}
