package AgileSalary.Command

import AgileSalary.Model.*
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

class AddEmp(val args: List<String>): Command {
    val empID: Int
        get() = args[0].toInt()
    val name: String
        get() = args[1]
    val address: String
        get() = args[2]
    val type: String
        get() = args[3]
    val data: List<String>
        get() = args.drop(4)

    override fun exec() = createEmployee()
    override fun validate() = println("validate AddEmp")

    fun createEmployee() {
        validateDuplicateID()

        val typeEnum = when(type) {
            "H" -> EmployeeType.HOURLY
            "S" -> EmployeeType.SALARY
            "C" -> EmployeeType.COMMISSION
            else -> throw IllegalArgumentException("Undefined Type: '$type'")
        }
        val cmd = this
        transaction {
            addLogger(StdOutSqlLogger)
            Employee.new(cmd.empID) {
                name = cmd.name
                address = cmd.address
                data1 = cmd.data.getOrElse(0, { "" })
                data2 = cmd.data.getOrElse(1, { "" })
                receiveMethod = ReceiveMethod.MAIL.index
                type = typeEnum.index
                lastPayday = LocalDateTime.now()
                createdAt = LocalDateTime.now()
                updatedAt = LocalDateTime.now()
            }
        }

    }

    fun validateDuplicateID() {
        val emp = transaction {
            Employee.findById(empID)
        }

        if (emp != null) {
            throw IllegalArgumentException("duplicate id: '$empID'")
        }
    }
}

class Help(val args: List<String>): Command {
    override fun exec() = println("exec Help!!")
    override fun validate() = println("validate Help!!")
}