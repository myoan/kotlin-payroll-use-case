package AgileSalary.Infrastructure

import AgileSalary.Model.HourlyEmployee
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

@Serializable
class JsonStore : DataStore {
    var filepath: String = ""
    var hourlyEmployees: MutableList<HourlyEmployee>

    companion object {
        fun load(filepath: String): JsonStore {
            val data = File(filepath).readText(Charsets.UTF_8)
            val ret = Json.decodeFromString<JsonStore>(data)
            ret.filepath = filepath
            return ret
        }
    }

    constructor(filepath: String) {
        hourlyEmployees = mutableListOf()
    }

    override fun add(emp: HourlyEmployee) {
        hourlyEmployees.add(emp)
        flush()
    }

    override fun delete(id: Int) {
        hourlyEmployees = hourlyEmployees.filter { it.id != id }.toMutableList()
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