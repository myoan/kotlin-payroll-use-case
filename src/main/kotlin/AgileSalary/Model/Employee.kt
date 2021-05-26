package AgileSalary.Model

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.IllegalArgumentException
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

enum class EmployeeType(val index: Int) {
    HOURLY(0),
    SALARY(1),
    COMMISSION(2)
}

enum class ReceiveMethod(val index: Int) {
    MAIL(0),
    HAND_OVER(1),
    BANK(2)
}

object Employees : IntIdTable("employees") {
    val name: Column<String> = varchar("name", 128)
    val address: Column<String> = varchar("address", 128)
    val data1: Column<String> = varchar("data1", 128)
    val data2: Column<String> = varchar("data2", 128)
    val receiveMethod: Column<Int> = integer("receive_method")
    val lastPayday: Column<LocalDateTime> = datetime("last_payday")
    val type: Column<Int> = integer("type")
    val createdAt: Column<LocalDateTime> = datetime("created_at")
    val updatedAt: Column<LocalDateTime> = datetime("updated_at")
}

fun LocalDateTime.isLastDayOfMonth(): Boolean {
    val tomorrow = this.plusDays(1)
    return this.month != tomorrow.month
}

class Employee(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Employee>(Employees)

    var name by Employees.name
    var address by Employees.address
    var data1 by Employees.data1
    var data2 by Employees.data2
    var receiveMethod by Employees.receiveMethod
    var lastPayday by Employees.lastPayday
    var type by Employees.type
    var createdAt by Employees.createdAt
    var updatedAt by Employees.updatedAt

    fun isPayday(date: LocalDateTime): Boolean {
        return when(type) {
            EmployeeType.HOURLY.index -> date.getDayOfWeek() == DayOfWeek.FRIDAY
            EmployeeType.SALARY.index -> date.isLastDayOfMonth()
            EmployeeType.COMMISSION.index -> {
                val base = LocalDateTime.of(2021, 5, 7, 0, 0, 0)
                val diff = ChronoUnit.DAYS.between(base, date)
                return (diff % 14).toInt() == 0
            }
            else -> false
        }
    }

    fun isUnion(): Boolean {
        val mid = id.value
        val union = transaction {
            UnionServiceCharges.slice(UnionServiceCharges.columns)
                .select { UnionServiceCharges.empID eq mid }.firstOrNull()
        }
        return union != null
    }
}