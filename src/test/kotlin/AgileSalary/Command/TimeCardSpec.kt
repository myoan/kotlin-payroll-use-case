package AgileSalary.Command

import AgileSalary.Model.*
import AgileSalary.Model.TimeCard as Model
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.*
import java.time.LocalDateTime
import org.jetbrains.exposed.sql.transactions.transaction

class TimeCardSpec: DescribeSpec({
    beforeTest {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
        transaction {
            SchemaUtils.drop(Employees, TimeCards)
            SchemaUtils.create(Employees, TimeCards)
        }
    }
    describe("exec") {
        describe("when employee not found") {
            it ("throw exception") {
                shouldThrow<IllegalArgumentException> {
                    TimeCard(listOf("1", "2021/5/19", "8")).exec()
                }
            }
        }

        describe("when employee is not Hourly") {
            beforeTest {
                transaction {
                    Employee.new(1) {
                        name = "name"
                        address = "address"
                        data1 = ""
                        data2 = ""
                        receiveMethod = ReceiveMethod.MAIL.index
                        type = EmployeeType.SALARY.index
                        lastPayday = LocalDateTime.now()
                        createdAt = LocalDateTime.now()
                        updatedAt = LocalDateTime.now()
                    }
                }
            }
            it ("throw exception") {
                shouldThrow<IllegalArgumentException> {
                    TimeCard(listOf("1", "2021/5/19", "8")).exec()
                }
            }
        }

        describe("when timecard not found") {
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
            it ("create timecard") {
                TimeCard(listOf("1", "2021/5/19", "8")).exec()
                transaction {
                    val tc = Model.find { TimeCards.empID eq 1 }.first()
                    tc.date shouldBe LocalDateTime.of(2021, 5, 19, 0, 0, 0)
                    tc.workingTime shouldBe 8
                }
            }
        }
    }
})
