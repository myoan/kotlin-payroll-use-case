import AgileSalary.Command.*
import AgileSalary.Command.TimeCard
import AgileSalary.Infrastructure.*
import AgileSalary.Model.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

fun setupDB(host: String, port: Int, db: String, user: String, pass: String) {
    Database.connect(
        url      = "jdbc:mysql://$host:$port/$db",
        driver   = "com.mysql.cj.jdbc.Driver",
        user     = user,
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
        "TimeCard" -> TimeCard(
            EmployeeRepositoryImpl,
            TimeCardRepositoryImpl,
            opt)
        else -> Help(opt)
    }
}

fun resetDatabase() {
    transaction {
        SchemaUtils.drop(
            Employees,
            TimeCards
        )
        SchemaUtils.create(
            Employees,
            TimeCards
        )
    }
}


fun main(args: Array<String>) {
    setupDB(
        host = "127.0.0.1",
        port = 3306,
        db   = "agile_salary",
        user = "root",
        pass = "root"
    )
    println(args.toList())

    resetDatabase()

    File(args[0]).forEachLine {
        println(it)
        val cmdArgs = it.split(" ")
        val cmd = parseCommand(cmdArgs)
        cmd.validate()
        cmd.exec()
    }
}