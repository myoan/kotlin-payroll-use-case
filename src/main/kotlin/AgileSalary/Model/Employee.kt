package AgileSalary.Model


interface Employee<T> {
    fun create(id: Int, name: String, address: String, data: List<String>): T
    fun delete(id: Int)
}