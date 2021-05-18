package AgileSalary.Command

import AgileSalary.Infrastructure.DataStore
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

class TimeCard(val args: List<String>): Command {
    val empID: Int
        get() = args[0].toInt()
    val date: LocalDateTime
        get() = LocalDateTime.parse(args[1])
    val workingTime: Int
        get() = args[2].toInt()

    override fun exec() = println("exec")
    override fun validate() = println("validate")

    fun insertTimeCard() {
        // val emp = db.findByEmployeeID(empID)

        // if (emp == null) {
        //     throw IllegalArgumentException("NotFound: id ($empID)")
        // }
        // emp.insertTimeCard(TimeCard(empID, date, workingTime))
        // db.TimeCard.create(empID, date, workingTime)
    }
}