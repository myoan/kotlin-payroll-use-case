package AgileSalary.Command

import AgileSalary.Infrastructure.MemoryDataStore
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.*

class DelEmpSpec: DescribeSpec({
    val db = MemoryDataStore()
    describe("exec") {
        beforeTest {
            db.clear()
        }
        describe ("when id exists") {
            beforeTest{
                AddEmp(db, listOf("1", "name", "address", "H", "hoge")).exec()
            }
            it ("delete employee") {
                DelEmp(db, listOf("1")).exec()
                db.employeesNum shouldBe 0
            }
        }

        describe ("when id not exist") {
            beforeTest{
                val args = listOf("1", "name", "address", "H", "hoge")
                val cmd = AddEmp(db, args)
                cmd.exec()
            }
            it ("throw exception") {
                shouldThrow<Exception> {
                    DelEmp(db, listOf("2")).exec()
                }
            }
        }
    }
})
