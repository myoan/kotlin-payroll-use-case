package AgileSalary.Infrastructure

import AgileSalary.Model.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

@Serializable
class JsonStore : DataStore {
    var filepath: String = ""
    var hourlyEmployees: MutableList<HourlyEmployee>
    var salaryEmployees: MutableList<SalaryEmployee>
    var commissionEmployees: MutableList<CommissionEmployee>

    companion object {
        fun load(filepath: String): JsonStore {
            val data = File(filepath).readText(Charsets.UTF_8)
            val ret = Json.decodeFromString<JsonStore>(data)
            ret.filepath = filepath
            return ret
        }
    }

    constructor() {
        hourlyEmployees = mutableListOf()
        salaryEmployees = mutableListOf()
        commissionEmployees = mutableListOf()
    }

    override fun add(emp: HourlyEmployee) {
        hourlyEmployees.add(emp)
        flush()
    }

    override fun add(emp: SalaryEmployee) {
        salaryEmployees.add(emp)
        flush()
    }

    override fun add(emp: CommissionEmployee) {
        commissionEmployees.add(emp)
        flush()
    }

    override fun delete(id: Int) {
        hourlyEmployees = hourlyEmployees.filter { it.id != id }.toMutableList()
        salaryEmployees = salaryEmployees.filter { it.id != id }.toMutableList()
        commissionEmployees = commissionEmployees.filter { it.id != id }.toMutableList()
    }

    override fun findByEmployeeID(id: Int): Employee? {
        val hEmp = hourlyEmployees.find { it.id == id }
        if (hEmp != null) return hEmp

        val sEmp = salaryEmployees.find { it.id == id }
        if (sEmp != null) return sEmp

        val cEmp = commissionEmployees.find { it.id == id }
        if (cEmp != null) return cEmp
        return null
    }

    fun show() {
        val format = Json { prettyPrint = true }
        println(format.encodeToString(this))
    }

    private fun flush() {
        val format = Json { prettyPrint = true }
        val data = format.encodeToString(this)
        File(filepath).writeText(data)
    }
}