package AgileSalary.Command

class ChgEmp(val args: List<String>): Command {
    override fun exec() = println("exec")
    override fun validate() = println("validate")
}