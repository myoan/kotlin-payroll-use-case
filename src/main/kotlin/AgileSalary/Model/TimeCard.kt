package AgileSalary.Model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object TimeCards : IntIdTable("time_cards") {
    val empID: Column<Int> = integer("employee_id")
    val date: Column<LocalDateTime> = datetime("date")
    val workingTime: Column<Int> = integer("working_time")
    val createdAt: Column<LocalDateTime> = datetime("created_at")
    val updatedAt: Column<LocalDateTime> = datetime("updated_at")
    // val employee = reference("employee", Employees)
}

class TimeCard(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TimeCard>(TimeCards)

    var empID by TimeCards.empID
    var date by TimeCards.date
    var workingTime by TimeCards.workingTime
    var createdAt by TimeCards.createdAt
    var updatedAt by TimeCards.updatedAt
    // var employee by Employee referencedOn TimeCards.employee
}