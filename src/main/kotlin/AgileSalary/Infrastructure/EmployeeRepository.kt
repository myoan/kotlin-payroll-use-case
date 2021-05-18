package AgileSalary.Infrastructure

import AgileSalary.Model.Employee
import AgileSalary.Model.EmployeeType
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

interface EmployeeRepository {
    fun create(
        _id: Int,
        _name: String,
        _address: String,
        _data: List<String>,
        _type: EmployeeType
    )

    fun findByID(id: Int) : Employee?
    fun deleteByID(id: Int)
}

object EmployeeRepositoryImpl : EmployeeRepository {
    override fun create(
        _id: Int,
        _name: String,
        _address: String,
        _data: List<String>,
        _type: EmployeeType
    ) {
        transaction {
            addLogger(StdOutSqlLogger)
            Employee.new(_id) {
                name = _name
                address = _address
                data1 = _data.getOrElse(0, { "" })
                data2 = _data.getOrElse(1, { "" })
                type = _type.index
            }
        }
    }

    override fun findByID(id: Int): Employee? {
        return transaction {
            Employee.findById(id)
        }
    }

    override fun deleteByID(id: Int) {
        val emp = findByID(id)
        emp ?: return

        emp.delete()
    }
}