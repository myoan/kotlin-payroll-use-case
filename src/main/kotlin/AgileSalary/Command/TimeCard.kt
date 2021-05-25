package AgileSalary.Command

import AgileSalary.Model.Employee
import AgileSalary.Model.EmployeeType
import AgileSalary.Model.TimeCard
import AgileSalary.Model.TimeCards
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId

class TimeCard(val args: List<String>): Command {
    val empID: Int
        get() = args[0].toInt()
    val date: LocalDateTime
        get() {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val date = sdf.parse(args[1])
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
        }
    val workingTime: Int
        get() = args[2].toInt()

    override fun exec() = insertTimeCard()
    override fun validate() = println("validate")

    fun insertTimeCard() {
        val args = this
        val emp = transaction {
            addLogger(StdOutSqlLogger)
            Employee.findById(empID)
        }

        if (emp == null) {
            throw IllegalArgumentException("employee not found: '$empID'")
        }
        if (emp.type != EmployeeType.HOURLY.index) {
            throw IllegalArgumentException("employee is not hourly: '$empID'")
        }

        transaction {
            addLogger(StdOutSqlLogger)
            val query = TimeCards.slice(TimeCards.columns)
                .select { (TimeCards.empID eq empID) and (TimeCards.date eq date) }
            val tc = TimeCard.wrapRows(query).firstOrNull() ?: TimeCard.new {
                empID = args.empID
                date = args.date
                workingTime = 0
                createdAt = LocalDateTime.now()
                updatedAt = LocalDateTime.now()
            }

            tc.workingTime = workingTime
        }
    }
}