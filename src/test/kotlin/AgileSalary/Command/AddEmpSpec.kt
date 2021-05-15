package AgileSalary.Command

import AgileSalary.Infrastructure.DataStore
import AgileSalary.Infrastructure.MemoryDataStore
import AgileSalary.Model.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.*
import java.lang.IllegalArgumentException

class AddEmpSpec: DescribeSpec({
    val db = MemoryDataStore()
    describe ("exec") {
        beforeTest {
            db.clear()
        }
        describe ("when type is Hourly employee") {
           val args = listOf("1", "name", "address", "H", "hoge")
           val cmd = AddEmp(db, args)
           it ("add Hourly employee") {
               cmd.exec()
               db.hourlyEmployees.size shouldBe 1
               db.salaryEmployees.size shouldBe 0
           }
        }

        describe ("when type is Salary employee") {
            val args = listOf("1", "name", "address", "S", "hoge")
            val cmd = AddEmp(db, args)
            it ("add salary employee") {
                cmd.exec()
                db.hourlyEmployees.size shouldBe 0
                db.salaryEmployees.size shouldBe 1
            }
        }

        describe ("when type is Comission employee") {
            val args = listOf("1", "name", "address", "C", "hoge", "fuga")
            val cmd = AddEmp(db, args)
            it ("add commission employee") {
                cmd.exec()
                db.hourlyEmployees.size shouldBe 0
                db.salaryEmployees.size shouldBe 0
                db.commissionEmployees.size shouldBe 1
            }
        }

        describe ("when type is invalid") {
            val args = listOf("1", "name", "address", "X", "hoge")
            val cmd = AddEmp(db, args)
            it ("throw exception") {
                shouldThrow<IllegalArgumentException> {
                    cmd.exec()
                }
            }
        }

        describe ("when duplicate entry in same employee type") {
            beforeTest {
                val cmd = AddEmp(db, listOf("1", "hoge", "address", "H", "hoge"))
                cmd.exec()
            }
            it ("throw exception") {
                shouldThrow<Exception> {
                    val cmd2 = AddEmp(db, listOf("1", "fuga", "address", "H", "hoge"))
                    cmd2.exec()
                }
            }
        }
    }
})