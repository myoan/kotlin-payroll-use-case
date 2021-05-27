package AgileSalary.Model

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class EmployeeSpec: DescribeSpec({
    beforeTest {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
        transaction {
            SchemaUtils.drop(Employees, UnionServiceCharges)
            SchemaUtils.create(Employees, UnionServiceCharges)
        }
    }

    describe("isPayday") {
        describe("hourly employee") {
            val employee = transaction {
                Employee.new {
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

            context("when not friday") {
                val day = LocalDateTime.of(2021, 5, 20, 0, 0, 0)
                it("returns false") {
                    employee.isPayday(day) shouldBe false
                }
            }
            context("when friday") {
                val day = LocalDateTime.of(2021, 5, 21, 0, 0, 0)
                it("returns false") {
                    employee.isPayday(day) shouldBe true
                }
            }
        }

        describe("salary employee") {
            val employee = transaction {
                Employee.new {
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

            context("when not last day of month") {
                val day = LocalDateTime.of(2021, 5, 20, 0, 0, 0)
                it("returns false") {
                    employee.isPayday(day) shouldBe false
                }
            }
            context("when last day of month") {
                val day = LocalDateTime.of(2021, 5, 31, 0, 0, 0)
                it("returns false") {
                    employee.isPayday(day) shouldBe true
                }
            }
        }

        describe("commission employee") {
            val employee = transaction {
                Employee.new {
                    name = "name"
                    address = "address"
                    data1 = ""
                    data2 = ""
                    receiveMethod = ReceiveMethod.MAIL.index
                    type = EmployeeType.COMMISSION.index
                    lastPayday = LocalDateTime.now()
                    createdAt = LocalDateTime.now()
                    updatedAt = LocalDateTime.now()
                }
            }

            context("when the first friday") {
                val day = LocalDateTime.of(2021, 5, 7, 0, 0, 0)
                it("returns false") {
                    employee.isPayday(day) shouldBe true
                }
            }
            context("when the second friday") {
                val day = LocalDateTime.of(2021, 5, 14, 0, 0, 0)
                it("returns false") {
                    employee.isPayday(day) shouldBe false
                }
            }
            context("when the third friday") {
                val day = LocalDateTime.of(2021, 5, 21, 0, 0, 0)
                it("returns false") {
                    employee.isPayday(day) shouldBe true
                }
            }
            context("when the fourth friday") {
                val day = LocalDateTime.of(2021, 5, 29, 0, 0, 0)
                it("returns false") {
                    employee.isPayday(day) shouldBe false
                }
            }
        }
    }

    describe("isUnion") {
        val employee = transaction {
            Employee.new {
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

        context("when employee not in union") {
            it("returns false") { employee.isUnion() shouldBe false }
        }
        context("when employee in union") {
            beforeTest {
                transaction {
                    UnionServiceCharge.new {
                        empID = employee.id.value
                        amount = 0
                        createdAt = LocalDateTime.now()
                        updatedAt = LocalDateTime.now()
                    }
                }
            }
            it("returns true") { employee.isUnion() shouldBe true }
        }
    }

    describe("unionFee") {
        val employee = transaction {
            Employee.new {
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
        context("employee not in union") {
            val date = LocalDateTime.now()
            it("returns 0") { employee.unionFee(date) shouldBe 0 }
        }

        context("employee in union") {
            beforeTest {
                transaction {
                    UnionServiceCharge.new {
                        empID = employee.id.value
                        amount = 10
                        createdAt = LocalDateTime.now()
                        updatedAt = LocalDateTime.now()
                    }
                }
            }
            val date = LocalDateTime.now()
            it ("returns 10") { employee.unionFee(date) shouldBe 10}
        }

        context("employee not pay union fee 2 week") {
            val date = LocalDateTime.now()
            beforeTest {
                transaction {
                    employee.lastPayday = date.minusDays(13)
                    UnionServiceCharge.new {
                        empID = employee.id.value
                        amount = 10
                        createdAt = date
                        updatedAt = date
                    }
                }
            }
            it ("returns 20") { employee.unionFee(date) shouldBe 20}
        }
    }
})
