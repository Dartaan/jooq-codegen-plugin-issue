package test.app

import test.db.Config
import test.db.Db

fun main(args : Array<String>) {
    val r = Db(Config.TestConfig()).testConnn()
    println(r)
}