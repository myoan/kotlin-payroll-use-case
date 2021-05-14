package AgileSalary.Infrastructure

import AgileSalary.Model.*

interface DataStore {
    fun add(emp: HourlyEmployee)
    fun add(emp: SalaryEmployee)
    fun add(emp: CommissionEmployee)
    fun delete(id: Int)
}