package AgileSalary.Model

import kotlinx.serialization.Serializable

@Serializable
open class Employee(val id: Int,
               val name: String,
               val address: String,
               val data: List<String>
) {}