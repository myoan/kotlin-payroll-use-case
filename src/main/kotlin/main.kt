import AgileSalary.Command.*
import AgileSalary.Infrastructure.*

fun parseCommand(db: DataStore, args: List<String>): Command {
    val cmd = args.getOrElse(0) { "help" }
    val opt = args.drop(1)
    return when(cmd) {
        "AddEmp" -> AddEmp(db, opt)
        "ChgEmp" -> ChgEmp(db, opt)
        "DelEmp" -> DelEmp(db, opt)
        "PayDay" -> Payday(db, opt)
        "SalesReceipt" -> SalesReceipt(db, opt)
        "ServiceCharge" -> ServiceCharge(db, opt)
        "TimeCard" -> TimeCard(db, opt)
        else -> Help(db, opt)
    }
}

fun main(args: Array<String>) {
    val db = JsonStore.load("./data.json")
    val cmd = parseCommand(db, args.toList())
    cmd.validate()
    cmd.exec()
    db.show()
}