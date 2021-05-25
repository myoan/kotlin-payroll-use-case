package AgileSalary.Model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime


object UnionServiceCharges : IntIdTable("union_service_charges") {
    val empID: Column<Int> = integer("employee_id")
    val amount: Column<Int> = integer("amount")
    val createdAt: Column<LocalDateTime> = datetime("created_at")
    val updatedAt: Column<LocalDateTime> = datetime("updated_at")
}

class UnionServiceCharge(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UnionServiceCharge>(UnionServiceCharges) {
        val FEE = 100
    }

    var empID by UnionServiceCharges.empID
    var amount by UnionServiceCharges.amount
    var createdAt by UnionServiceCharges.createdAt
    var updatedAt by UnionServiceCharges.updatedAt
}