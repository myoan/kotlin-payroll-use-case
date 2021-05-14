package AgileSalary.Infrastructure

import AgileSalary.Model.CommissionEmployee
import AgileSalary.Model.HourlyEmployee
import AgileSalary.Model.SalaryEmployee

class MemoryDataStore: DataStore {
    var hourlyEmployees: MutableList<HourlyEmployee>
    var salaryEmployees: MutableList<SalaryEmployee>
    var commissionEmployees: MutableList<CommissionEmployee>

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

    override fun delete(id: Int) {}

    fun clear() {
        hourlyEmployees = mutableListOf()
        salaryEmployees = mutableListOf()
    }
}