package AgileSalary.Command

import AgileSalary.Infrastructure.DataStore
import java.lang.IllegalArgumentException

class DelEmp(val db: DataStore, val args: List<String>): Command {
    val empID: Int
        get() = args[0].toInt()

    override fun exec() {
        if (!isExists()) {
            throw IllegalArgumentException("NotFound: id ($empID)")
        }
        db.delete(empID)
    }
    override fun validate() = println("validate")

    fun isExists(): Boolean = db.findByEmployeeID(empID) != null
}