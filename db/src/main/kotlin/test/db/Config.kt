package test.db

import org.jooq.SQLDialect
import org.jooq.SQLDialect.ORACLE

interface Config {

    val url: String
    val username: String
    val password: String
    val dialect: SQLDialect

    class TestConfig : Config {
        override val url = ""
        override val username = ""
        override val password = ""
        override val dialect = ORACLE
    }
}