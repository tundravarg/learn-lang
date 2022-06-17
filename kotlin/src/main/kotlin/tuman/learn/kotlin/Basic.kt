fun learnBasic() {
//    learnVariables()
    learnBasicStatements()
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
