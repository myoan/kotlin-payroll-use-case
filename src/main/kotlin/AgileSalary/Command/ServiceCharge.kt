package AgileSalary.Command

import AgileSalary.Model.*
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalArgumentException

class ServiceCharge(val args: List<String>): Command {
    val empID: Int
        get() = args[0].toInt()
    val amount: Int
        get() = args[2].toInt()

    override fun exec() = createUnionServiceCharge()
    override fun validate() = println("validate")

    fun createUnionServiceCharge() {
        val args = this
        val emp = transaction {
            addLogger(StdOutSqlLogger)
            Employee.findById(empID)
        }

        if (emp == null) {
            throw IllegalArgumentException("employee not found: '$empID'")
        }

        transaction {
            addLogger(StdOutSqlLogger)
            val usc = UnionServiceCharge.find { UnionServiceCharges.empID eq empID }.firstOrNull() ?: UnionServiceCharge.new {
                empID = args.empID
                amount = 0
            }

            usc.amount = args.amount
        }
    }
}