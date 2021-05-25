package AgileSalary.Command

import AgileSalary.Model.*
import AgileSalary.Model.SalesReceipt
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId

class SalesReceipt(val args: List<String>): Command {
    val empID: Int
        get() = args[0].toInt()
    val date: LocalDateTime
        get() {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val date = sdf.parse(args[1])
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
        }
    val amount: Int
        get() = args[2].toInt()

    override fun exec() = createReceipt()
    override fun validate() = println("validate")

    fun createReceipt() {
        val args = this
        val emp = transaction {
            addLogger(StdOutSqlLogger)
            Employee.findById(empID)
        }

        if (emp == null) {
            throw IllegalArgumentException("employee not found: '$empID'")
        }
        if (emp.type != EmployeeType.COMMISSION.index) {
            throw IllegalArgumentException("employee is not commission: '$empID'")
        }

        transaction {
            addLogger(StdOutSqlLogger)
            val query = SalesReceipts.slice(SalesReceipts.columns)
                .select { (SalesReceipts.empID eq empID) and (SalesReceipts.date eq date) }
            val sr = SalesReceipt.wrapRows(query).firstOrNull() ?: SalesReceipt.new {
                empID = args.empID
                date = args.date
                amount = 0
            }

            sr.amount = amount
        }
    }
}