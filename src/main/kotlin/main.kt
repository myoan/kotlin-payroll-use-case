import AgileSalary.Command.*
import AgileSalary.Infrastructure.*
import org.jetbrains.exposed.sql.Database

fun setupDB(host: String, port: Int, db: String, user: String, pass: String) {
    Database.connect(
        "jdbc:mysql://$host:$port/$db",
        driver = "com.mysql.cj.jdbc.Driver",
        user = user,
        password = pass)
}

fun parseCommand(args: List<String>): Command {
    val cmd = args.getOrElse(0) { "help" }
    val opt = args.drop(1)
    return when(cmd) {
        "AddEmp" -> AddEmp(EmployeeRepositoryImpl, opt)
        "ChgEmp" -> ChgEmp(opt)
        "DelEmp" -> DelEmp(EmployeeRepositoryImpl, opt)
        "PayDay" -> Payday(opt)
        "SalesReceipt" -> SalesReceipt(opt)
        "ServiceCharge" -> ServiceCharge(opt)
        "TimeCard" -> TimeCard(opt)
        else -> Help(opt)
    }
}

fun main(args: Array<String>) {
    setupDB("127.0.0.1", 3306, "agile_salary", "root", "root")
    println(args.toList())
    val cmd = parseCommand(args.toList())
    cmd.validate()
    cmd.exec()
    // db.show()
}