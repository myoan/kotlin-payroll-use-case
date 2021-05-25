package AgileSalary.Command

import AgileSalary.Model.Employee
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalArgumentException

class DelEmp(val args: List<String>): Command {
    val empID: Int
        get() = args[0].toInt()

    override fun exec() {
        transaction {
            val emp = Employee.findById(empID)
            emp ?: throw IllegalArgumentException("NotFound: id ($empID)")

            emp.delete()
        }
    }
    override fun validate() = println("validate")
}