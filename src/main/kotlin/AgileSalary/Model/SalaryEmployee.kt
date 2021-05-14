package AgileSalary.Model

import kotlinx.serialization.*

@Serializable
data class SalaryEmployee(val id: Int, val name: String, val address: String, val data: List<String>) {}