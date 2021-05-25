package AgileSalary.Model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object Salaries : IntIdTable("salaries") {
    val empID: Column<Int> = integer("employee_id")
    val paid_at: Column<LocalDateTime> = datetime("paid_at")
    val salary: Column<Int> = integer("salary")
    val createdAt: Column<LocalDateTime> = datetime("created_at")
    val updatedAt: Column<LocalDateTime> = datetime("updated_at")
}

class Salary(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Salary>(Salaries)

    var empID  by Salaries.empID
    var paid_at by Salaries.paid_at
    var salary by Salaries.salary
    var createdAt by Salaries.createdAt
    var updatedAt by Salaries.updatedAt
}