package test.db

import org.jooq.Record
import org.jooq.Result
import org.jooq.impl.DSL.using
import java.sql.DriverManager.getConnection

class Db(private val config: Config) {

    fun testConnn(): Result<Record> {
        val conn = getConnection(config.url, config.username, config.password)
        val s = using(conn, config.dialect)
            .select()
            .from("V\$VERSION")
            .queryTimeout(30)
            .fetch()
        conn.close()
        return s
    }
}