package AgileSalary.Infrastructure

import AgileSalary.Model.CommissionEmployee
import AgileSalary.Model.Employee
import AgileSalary.Model.HourlyEmployee
import AgileSalary.Model.SalaryEmployee

class MemoryDataStore: DataStore {
    var hourlyEmployees: MutableList<HourlyEmployee>
    var salaryEmployees: MutableList<SalaryEmployee>
    var commissionEmployees: MutableList<CommissionEmployee>
    override val employeesNum: Int
        get() = listOf(hourlyEmployees, salaryEmployees, commissionEmployees).sumOf { it.size }

    init {
        hourlyEmployees = mutableListOf()
        salaryEmployees = mutableListOf()
        commissionEmployees = mutableListOf()
    }

    override fun add(emp: HourlyEmployee) {
        hourlyEmployees.add(emp)
    }

    override fun add(emp: SalaryEmployee) {
        salaryEmployees.add(emp)
    }
    override fun add(emp: CommissionEmployee) {
        commissionEmployees.add(emp)
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

    fun clear() {
        hourlyEmployees = mutableListOf()
        salaryEmployees = mutableListOf()
        commissionEmployees = mutableListOf()
    }
}