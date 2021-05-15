package AgileSalary.Model

import kotlinx.serialization.Serializable

@Serializable
class HourlyEmployee: Employee {
    constructor(id: Int, name: String, address: String, data: List<String>): super(id, name, address, data) {}
}
