package AgileSalary.Infrastructure

import AgileSalary.Model.*

interface DataStore {
    val employeesNum: Int
    fun add(emp: HourlyEmployee)
    fun add(emp: SalaryEmployee)
    fun add(emp: CommissionEmployee)
    fun delete(id: Int)
    fun findByEmployeeID(id: Int): Employee?
}