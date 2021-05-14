package AgileSalary.Command

import AgileSalary.Infrastructure.DataStore

class TimeCard(val db: DataStore, val args: List<String>): Command {
    override fun exec() = println("exec")
    override fun validate() = println("validate")
}