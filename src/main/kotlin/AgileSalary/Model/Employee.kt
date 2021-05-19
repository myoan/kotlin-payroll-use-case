package AgileSalary.Model

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

enum class EmployeeType(val index: Int) {
    HOURLY(0),
    SALARY(1),
    COMMISSION(2)
}

object Employees : IntIdTable("employees") {
    val name: Column<String> = varchar("name", 128)
    val address: Column<String> = varchar("address", 128)
    val data1: Column<String> = varchar("data1", 128)
    val data2: Column<String> = varchar("data2", 128)
    val type: Column<Int> = integer("type")
}

class Employee(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Employee>(Employees)

    var name by Employees.name
    var address by Employees.address
    var data1 by Employees.data1
    var data2 by Employees.data2
    var type by Employees.type

    // val timecards by TimeCard referrersOn TimeCards.employee

    fun getOrCreateTimeCardByDate(_date: LocalDateTime): TimeCard {
        val emp = this
        return transaction {
            val records = TimeCard.find { TimeCards.date eq _date }
            records.firstOrNull() ?: TimeCard.new {
                empID = emp.id.value
                date = _date
                workingTime = 0
            }
        }
    }
}