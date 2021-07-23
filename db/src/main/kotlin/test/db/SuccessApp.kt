package test.db

fun main(args : Array<String>) {
    val r = Db(Config.TestConfig()).testConnn()
    println(r)
}