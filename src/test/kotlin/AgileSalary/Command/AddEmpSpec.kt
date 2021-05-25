package AgileSalary.Command

import AgileSalary.Model.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class AddEmpSpec: DescribeSpec({
    beforeTest {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
        transaction {
            SchemaUtils.drop(Employees, TimeCards)
            SchemaUtils.create(Employees, TimeCards)
        }
    }

    describe ("exec") {
        describe ("when type is Hourly employee") {
            it ("add Hourly employee") {
                AddEmp(listOf("1", "name", "address", "H", "hoge")).exec()
                transaction {
                    Employee.findById(1) shouldNotBe null
                }
            }
        }

        describe ("when type is Salary employee") {
            it ("add salary employee") {
                AddEmp(listOf("1", "name", "address", "S", "hoge")).exec()
                transaction {
                    Employee.findById(1) shouldNotBe null
                }
            }
        }

        describe ("when type is Commission employee") {
            it ("add salary employee") {
                AddEmp(listOf("1", "name", "address", "C", "hoge")).exec()
                transaction {
                    Employee.findById(1) shouldNotBe null
                }
            }
        }

        describe ("when type is invalid") {
            it ("throw exception") {
                shouldThrow<IllegalArgumentException> {
                    AddEmp(listOf("1", "name", "address", "X", "hoge")).exec()
                }
            }
        }

        describe ("when duplicate entry") {
            beforeTest {
                transaction {
                    Employee.new(1) {
                        name = "name"
                        address = "address"
                        data1 = ""
                        data2 = ""
                        receiveMethod = ReceiveMethod.MAIL.index
                        type = EmployeeType.HOURLY.index
                        lastPayday = LocalDateTime.now()
                        createdAt = LocalDateTime.now()
                        updatedAt = LocalDateTime.now()
                    }
                }
            }

            it ("throw exception") {
                shouldThrow<Exception> {
                    AddEmp(listOf("1", "fuga", "address", "H", "hoge")).exec()
                }
            }
        }
    }
})