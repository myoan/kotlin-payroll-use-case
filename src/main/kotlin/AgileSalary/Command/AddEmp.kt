package AgileSalary.Command

import AgileSalary.Infrastructure.DataStore
import AgileSalary.Model.HourlyEmployee

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
        when(type) {
            "H" -> db.add(HourlyEmployee(empID, name, address, data))
        }
    }
}

class Help(val db: DataStore, val args: List<String>): Command {
    override fun exec() = println("exec Help!!")
    override fun validate() = println("validate Help!!")
}