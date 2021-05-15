package AgileSalary.Command

import AgileSalary.Infrastructure.DataStore
import AgileSalary.Model.*
import java.lang.Exception
import java.lang.IllegalArgumentException

class AddEmp(val db: DataStore, val args: List<String>): Command {
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

        when(type) {
            "H" -> db.add(HourlyEmployee(empID, name, address, data))
            "S" -> db.add(SalaryEmployee(empID, name, address, data))
            "C" -> db.add(CommissionEmployee(empID, name, address, data))
            else -> throw IllegalArgumentException("Undefined Type: '$type'")
        }
    }

    fun validateDuplicateID() {
        val emp = db.findByEmployeeID(empID)

        if (emp != null) {
            throw IllegalArgumentException("duplicate id: '$empID'")
        }
    }
}

class Help(val db: DataStore, val args: List<String>): Command {
    override fun exec() = println("exec Help!!")
    override fun validate() = println("validate Help!!")
}