fun learnBasic() {
//    learnVariables()
//    learnBasicStatements()
//    learnFunctions()
    learnLambdas()
}


private fun learnVariables() {
    println("-- learnVariables --")

    var a = 0;
    a++;

    val b = 0;
    // b++;    // ILLEGAL: Val cannot be reassigned

    var varByte:   Byte   = 127                    // NOTE: Illegal values: 1L, 128
    var varShort:  Short  = 32767                  // NOTE: Illegal values: 1L, 32768
    var varInt:    Int    = 2147483647             // NOTE: Illegal values: 1L, 2147483648
    var varLong:   Long   = 9223372036854775807    // NOTE: Illegal values: 9223372036854775808
    var varFloat:  Float  = 1.0F                   // NOTE: Literals without `F` suffix are illegal: 1 and 1.0
    var varDouble: Double = 1.0

    // varByte = varShort    // ILLEGAL
    // varShort = varByte    // ILLEGAL
    varByte = varShort.toByte()
    varShort = varByte.toShort()

    // varInt = null    // ILLEGAL

    var varIntOpt: Int? = 1
    varInt = if (varIntOpt != null) varIntOpt else 0
    varInt = varIntOpt ?: 0

    var nop = 0;
}


private fun learnBasicStatements() {
    println("-- Basic Statements --")

    var x0 = 15.0
    var x1 = 5.0
    println("0) [$x0, $x1]")

    // `If` statement
    if (x0 > x1) {
        // Swap values
        x0 = x1.also { x1 = x0 }
    }
    println("1) [$x0, $x1]")

    // `If` expression
    var minX = if (x0 < x1) x0 else x1
    var maxX = if (x0 > x1) x0 else x1
    println("min: $minX, max: $maxX")

    var x = 10.0
    if (x in x0..x1) {
        println("$x is in [$x0..$x1]")
    }
    if (x !in x1..x0) {
        println("$x is not in [$x1..$x0]")
    }

    // Range loop
    for (i in 0..10 step 2) {
        print("$i ")
    }
    println()

    // Down stream range loop
    for (i in 10 downTo 0 step 2) {
        print("$i ")
    }
    println()

    // Iterate over list
    for (i in listOf<String>("Uno", "Dos", "Tres")) {
        print("$i ")
    }
    println()

    // Iterate over list reversed
    for (i in listOf<String>("Uno", "Dos", "Tres").reversed()) {
        print("$i ")
    }
    println()

    // Iterate over map
    for ((k, v) in mapOf<String, Int>("Uno" to 1, "Dos" to 2, "Tres" to 3)) {
        print("$k=$v ")
    }
    println()

    // While loop
    x = 0.5
    while (x < maxX) {
        print("$x ")
        x *= 1.5
    }
    println()

    // `when` statement
    when {
        x in minX..maxX -> println("$x is in [$minX..$maxX]")
        x !in minX..maxX -> println("$x is not in [$minX..$maxX]")
        else -> println("Something else")
    }

    // `when` expression
    x = when {
        x < 5.0 -> 5.0
        x < 10.0 -> 10.0
        x < 15.0 -> 15.0
        x < 20.0 -> 20.0
        else -> 9000.0
    }
    println("x: $x")

    // If-not-null-let
//    var x2: Double? = null
    var x2: Double? = 30.0
    x = x2?.let { x2 * 1.5 } ?: x1
    println("x: $x")
}


private fun learnFunctions() {
    println("-- Functions --")

    // Basic function

    fun isInRange(x: Double?, x0: Double?, x1: Double?): Boolean? {
        if (x == null) {
            return null
        }
        if (x0 != null && x < x0) {
            return false
        }
        if (x1 != null && x > x1) {
            return false;
        }
        return true;
    }

    fun testIsInRange(x: Double?, x0: Double?, x1: Double?) {
        val result = isInRange(x, x0, x1)
        val resultStr = if (result == true) "is in" else "is not in"
        val x0Str = if (x0 != null) "[$x0" else "(*"
        val x1Str = if (x1 != null) "$x1]" else "*)"
        println("$x $resultStr $x0Str, $x1Str")
    }

    testIsInRange(5.0, 0.0, 10.0)
    testIsInRange(5.0, 10.0, 0.0)
    testIsInRange(15.0, 0.0, 10.0)
    testIsInRange(null, 0.0, 10.0)
    testIsInRange(5.0, null, 10.0)
    testIsInRange(5.0, null, 2.0)
    testIsInRange(5.0, 0.0, null)
    testIsInRange(5.0, 7.0, null)
    testIsInRange(5.0, null, null)
    testIsInRange(null, null, null)

    println("--")

    // Function with return value instead of body

    fun min(x0: Double, x1: Double): Double = if (x0 < x1) x0 else x1

    println("min of 1 and 5 is ${min(1.0, 5.0)}")
    println("min of 9 and 5 is ${min(9.0, 5.0)}")
//    println("min of null and 5 is ${min(null, 5.0)}")    // ILLEGAL: null in non-nullable argument

    println("--")

    // Function with default parameters

    fun rectArea(width: Double, height: Double = width) = width * height

    println("Area of square 10x10 is ${rectArea(10.0)}")
    println("Area of rectangle 10x20 is ${rectArea(10.0, 20.0)}")

    // Call with named parameters
    println("Area of rectangle 10x30 is ${rectArea(width = 10.0, 30.0)}")
    println("Area of rectangle 10x40 is ${rectArea(height = 40.0, width = 10.0)}")
    println("Area of square 20x20 is ${rectArea(width = 20.0)}")

    println("--")

    // Lambdas

    fun withRectArea(width: Double, height: Double = width, process: (area: Double) -> Unit) {
        process.invoke(width * height)
    }
    fun printArea(name: String): (area: Double) -> Unit {
        return fun (area: Double) = println("Area of $name is $area")
    }

    withRectArea(10.0) { println("Area is ___") }
    withRectArea(20.0, 10.0, fun (area) = println("Area is $area"))
    withRectArea(30.0, 20.0, printArea("rectangle 30x30"))

    println("--")

    // Varargs

    fun min(vararg values: Double): Double? {
        if (values.isEmpty()) {
            return null
        }
        var result = values[0]
        for (v in values) {
            if (v < result) result = v
        }
        return result
    }

    println("Min of [4.0, 3.0, 2.0] is " + min(4.0, 3.0, 2.0))
    val values = arrayOf(5.5, 3.7, 8.7, 4.1, 6.6)
    println("Min of [${values.joinToString(", ")}] is " + min(*values.toDoubleArray()))

    println("--")

    // Infix

    infix fun Double.pwr(p: Int): Double {
        var r = 1.0
        for (i in 0 until p) r *= this
        return r
    }

    println("2^8=${2.0 pwr 8}")
}


fun learnLambdas() {
    println("-- Lambda --")

    val iadd: IntOp = { i0, i1 -> i0 + i1 }
    val isub: IntOp = fun(i0, i1) = i0 - i1
//    val imul: IntOp = { i1 -> this * i1}

    println("12 + 5 = ${iadd.invoke(12, 5)}")
    println("17 - 5 = ${isub(17, 5)}")

    val imul: IntOp2 = { i1 -> this * i1 }
    val idiv: IntOp2 = fun Int.(i1: Int): Int = this / i1

    println("2 * 5 = ${imul(2, 5)}")
    println("2 * 3 = ${2.imul(3)}")
    println("9 / 2 = ${idiv(9, 2)}")
    println("9 / 3 = ${9.idiv(3)}")

    val iadd2: IntOp2 = iadd
    val iadd3: IntOp = iadd2

    println("2 + 6 = ${2.iadd2(6)}")
    println("2 + 7 = ${iadd2(2, 7)}")
//    println("2 + 8 = ${2.isum3(8)}")
    println("2 + 9 = ${iadd3(2, 9)}")

    fun log(name: String, op: IntOp): IntOp {
        return { a, b ->
            val r = op(a, b)
            println("$a $name $b = $r")
            r
        }
    }

    log("plus", iadd)(5, 6)
    log("minus", isub)(9, 3)
    log("multiple", imul)(3, 5)
    log("divide", idiv)(9, 3)
}

typealias IntOp = (Int, Int) -> Int
typealias IntOp2 = Int.(Int) -> Int
