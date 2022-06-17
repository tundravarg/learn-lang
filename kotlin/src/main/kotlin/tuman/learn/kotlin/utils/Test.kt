package tuman.learn.kotlin.utils


enum class TestLevel {
    L1, L2
}

fun test(name: String, level: TestLevel = TestLevel.L1, exec: (name: String) -> Unit) {
    val highlighter = when {
        level == TestLevel.L1 -> "=="
        else -> "--"
    }
    println("$highlighter $name $highlighter")
    exec(name)
    println("$highlighter$highlighter")
}

fun subtest(name: String, exec: (name: String) -> Unit) {
    test(name, TestLevel.L2, exec)
}