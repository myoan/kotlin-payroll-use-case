package AgileSalary.Command

import AgileSalary.Infrastructure.EmployeeRepository
import java.lang.IllegalArgumentException

class DelEmp(val repo: EmployeeRepository, val args: List<String>): Command {
    val empID: Int
        get() = args[0].toInt()

    override fun exec() {
        if (!isExists()) {
            throw IllegalArgumentException("NotFound: id ($empID)")
        }
        repo.deleteByID(empID)
    }
    override fun validate() = println("validate")

    fun isExists(): Boolean = repo.findByID(empID) != null
}