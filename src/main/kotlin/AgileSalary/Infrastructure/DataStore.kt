package AgileSalary.Infrastructure

import AgileSalary.Model.HourlyEmployee

interface DataStore {
    fun add(emp: HourlyEmployee)
    fun delete(id: Int)
}