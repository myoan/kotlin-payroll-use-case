package AgileSalary.Command

import AgileSalary.Model.Employee
import AgileSalary.Model.Salary
import AgileSalary.Model.SalaryCaclurator
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId

class Payday(val args: List<String>): Command {
    val date: LocalDateTime
        get() {
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            val date = sdf.parse(args[0])
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
        }

    override fun exec() = pay()
    override fun validate() = println("validate")

    fun pay() {
        val cmd = this

        transaction {
            Employee.all().forEach {
                val emp = it
                if (emp.isPayday(date)) {
                    val empSalary = SalaryCaclurator.calcurate(emp)
                    Salary.new {
                        empID = emp.id.value
                        paid_at = cmd.date
                        salary = empSalary
                    }
                }
            }
        }
    }
}