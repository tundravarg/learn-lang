package tuman.learn.kotlin


import tuman.learn.kotlin.subpackage.defaultFun
import tuman.learn.kotlin.subpackage.publicFun
// import tuman.learn.kotlin.subpackage.privateFun    // ILLEGAL: `private` access


fun main(args: Array<String>) {
    System.out.println("Learn Kotlin " + (if (args.size > 0) args[0] else ""))
    learnAccessModifiers()
}


private fun learnAccessModifiers() {
    println("-- accessModifiers --")

    defaultFun()
    publicFun()
    // privateFun()    // ILLEGAL: `private` access
}
