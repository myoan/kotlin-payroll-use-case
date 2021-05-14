package AgileSalary.Command

import AgileSalary.Infrastructure.DataStore

class DelEmp(val db: DataStore, val args: List<String>): Command {
    val empID: Int
        get() = args[0].toInt()

    override fun exec() = db.delete(empID)
    override fun validate() = println("validate")
}