package tuman.learn.kotlin


import tuman.learn.kotlin.subpackage.defaultFun
import tuman.learn.kotlin.subpackage.publicFun
import learnBasic
// import tuman.learn.kotlin.subpackage.privateFun    // ILLEGAL: `private` access


fun main(args: Array<String>) {
    System.out.println("Learn Kotlin " + (if (args.size > 0) args[0] else ""))

//    learnAccessModifiers()
//    learnBasic()
    learnKotlinClasses()

//    tests()
}


private fun learnAccessModifiers() {
    println("-- Access Modifiers --")

    defaultFun()
    publicFun()
    // privateFun()    // ILLEGAL: `private` access
}
