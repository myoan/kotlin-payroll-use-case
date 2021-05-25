package AgileSalary.Model

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class SalaryCalcuratorSpec: DescribeSpec({
    beforeTest {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
        transaction {
            SchemaUtils.drop(Employees,TimeCards)
            SchemaUtils.create(Employees, TimeCards)
        }
    }

    describe("calcurate") {
        context("houry employee") {
            val employee = transaction {
                Employee.new {
                    name = "name"
                    address = "address"
                    data1 = "1000"
                    data2 = ""
                    receiveMethod = ReceiveMethod.MAIL.index
                    type = EmployeeType.HOURLY.index
                    lastPayday = LocalDateTime.now()
                    createdAt = LocalDateTime.now()
                    updatedAt = LocalDateTime.now()
                }
            }

            context("when no work") {
                it ("returns 0") {
                    SalaryCaclurator.calcurate(employee) shouldBe 0
                }
            }
            context("when not work overtime") {
                beforeTest {
                    transaction {
                        TimeCard.new {
                            empID = employee.id.value
                            date = LocalDateTime.of(2021, 1, 1, 0, 0, 0)
                            workingTime = 8
                            createdAt = LocalDateTime.now()
                            updatedAt = LocalDateTime.now()
                        }
                    }
                }
                it ("returns 8000") {
                    SalaryCaclurator.calcurate(employee) shouldBe 8000
                }
            }

            context("when multiple work") {
                beforeTest {
                    val baseDay = employee.lastPayday.minusDays(1)
                    transaction {
                        TimeCard.new {
                            empID = employee.id.value
                            date = baseDay
                            workingTime = 8
                            createdAt = baseDay
                            updatedAt = baseDay
                        }
                        TimeCard.new {
                            empID = employee.id.value
                            date = baseDay.plusDays(1)
                            workingTime = 7
                            createdAt = baseDay.plusDays(1)
                            updatedAt = baseDay.plusDays(1)
                        }
                    }
                }
                it ("returns after last payday") {
                    SalaryCaclurator.calcurate(employee) shouldBe 7000
                }
            }

            context("when work overtime") {
                beforeTest {
                    transaction {
                        TimeCard.new {
                            empID = employee.id.value
                            date = LocalDateTime.of(2021, 1, 1, 0, 0, 0)
                            workingTime = 10
                            createdAt = LocalDateTime.now()
                            updatedAt = LocalDateTime.now()
                        }
                    }
                }
                it ("returns 8000") {
                    SalaryCaclurator.calcurate(employee) shouldBe 11000
                }
            }

        }
    }
})

