package AgileSalary.Command

import AgileSalary.Infrastructure.EmployeeRepository
import AgileSalary.Infrastructure.TimeCardRepository
import AgileSalary.Model.EmployeeType
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId

class TimeCard(val empRepo: EmployeeRepository, val tcRepo: TimeCardRepository, val args: List<String>): Command {
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
        println("exec $empID $date $workingTime")

        val emp = empRepo.findByID(empID)
        if (emp == null) {
            throw IllegalArgumentException("employee not found: '$empID'")
        }
        if (emp.type != EmployeeType.HOURLY.index) {
            throw IllegalArgumentException("employee is not hourly: '$empID'")
        }

        val tc = emp.getOrCreateTimeCardByDate(date)
        transaction {
            tc.workingTime = workingTime
        }
    }
}