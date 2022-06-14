fun learnBasic() {
    learnVariables()
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
