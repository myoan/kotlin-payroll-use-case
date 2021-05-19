package AgileSalary.Infrastructure

import AgileSalary.Model.TimeCard
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

interface TimeCardRepository {
    fun create(
        _empID: Int,
        _date: LocalDateTime,
        _wt: Int
    )

    // fun findByEmployeeID(id: Int) : List<TimeCard>
}

object TimeCardRepositoryImpl : TimeCardRepository {
    override fun create(
        _empID: Int,
        _date: LocalDateTime,
        _wt: Int
    ) {
        transaction {
            addLogger(StdOutSqlLogger)
            TimeCard.new {
                empID = _empID
                date = _date
                workingTime = _wt
            }
        }
    }

    // override fun findByEmployeeID(id: Int): List<TimeCard> {
    //     return transaction {
    //         TimeCard.findById(id)
    //     }
    // }
}