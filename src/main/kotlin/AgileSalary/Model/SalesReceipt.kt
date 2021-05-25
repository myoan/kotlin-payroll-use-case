package AgileSalary.Model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime

object SalesReceipts : IntIdTable("sales_receipts") {
    val empID: Column<Int> = integer("employee_id")
    val date: Column<LocalDateTime> = datetime("date")
    val amount: Column<Int> = integer("amount")
    val createdAt: Column<LocalDateTime> = datetime("created_at")
    val updatedAt: Column<LocalDateTime> = datetime("updated_at")
}

class SalesReceipt(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SalesReceipt>(SalesReceipts)

    var empID by SalesReceipts.empID
    var date by SalesReceipts.date
    var amount by SalesReceipts.amount
    var createdAt by SalesReceipts.createdAt
    var updatedAt by SalesReceipts.updatedAt
}