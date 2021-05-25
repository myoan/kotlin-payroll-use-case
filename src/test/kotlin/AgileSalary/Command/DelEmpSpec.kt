package AgileSalary.Command

import AgileSalary.Model.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class DelEmpSpec: DescribeSpec({
    beforeTest {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
        transaction {
            SchemaUtils.drop(Employees, TimeCards)
            SchemaUtils.create(Employees, TimeCards)
        }
    }

    describe("exec") {
        describe("when id exists") {
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

            it("delete employee") {
                DelEmp(listOf("1")).exec()
                transaction {
                    Employee.findById(1) shouldBe null
                }
            }
       }

       describe ("when id not exist") {
           it ("throw exception") {
               shouldThrow<Exception> {
                   DelEmp(listOf("1")).exec()
               }
           }
       }
    }
})
