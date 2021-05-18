package AgileSalary.Command

import AgileSalary.Infrastructure.EmployeeRepository
import AgileSalary.Model.*
import java.lang.IllegalArgumentException

class AddEmp(val repo: EmployeeRepository, val args: List<String>): Command {
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
        repo.create(empID, name, address, data, typeEnum)
    }

    fun validateDuplicateID() {
        val emp = repo.findByID(empID)

        if (emp != null) {
            throw IllegalArgumentException("duplicate id: '$empID'")
        }
    }
}

class Help(val args: List<String>): Command {
    override fun exec() = println("exec Help!!")
    override fun validate() = println("validate Help!!")
}