package AgileSalary.Model

import kotlinx.serialization.*

@Serializable
data class HourlyEmployee(val id: Int, val name: String, val address: String, val data: List<String>) {}