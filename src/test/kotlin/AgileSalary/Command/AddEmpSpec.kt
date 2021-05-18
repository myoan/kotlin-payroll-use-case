package AgileSalary.Command

import AgileSalary.Infrastructure.EmployeeRepository
import AgileSalary.Model.Employee
import AgileSalary.Model.EmployeeType
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*

object repo : EmployeeRepository {
    override fun create(
        _id: Int,
        _name: String,
        _address: String,
        _data: List<String>,
        _type: EmployeeType
    ) {}

    override fun findByID(id: Int): Employee? = null
    override fun deleteByID(id: Int) {}
}

class AddEmpSpec: DescribeSpec({
    beforeTest {
        mockkObject(repo)
    }

    describe ("exec") {
        describe ("when type is Hourly employee") {
            justRun { repo.create(any(), any(), any(), any(), EmployeeType.HOURLY) }
            it ("add Hourly employee") {
                AddEmp(repo, listOf("1", "name", "address", "H", "hoge")).exec()
                verify(exactly = 1) { repo.create(any(), any(), any(), any(), EmployeeType.HOURLY) }
                verify(exactly = 0) { repo.create(any(), any(), any(), any(), EmployeeType.SALARY) }
                verify(exactly = 0) { repo.create(any(), any(), any(), any(), EmployeeType.COMMISSION) }
            }
        }

        describe ("when type is Salary employee") {
            justRun { repo.create(any(), any(), any(), any(), EmployeeType.SALARY) }
            it ("add salary employee") {
                AddEmp(repo, listOf("1", "name", "address", "S", "hoge")).exec()
                verify(exactly = 0) { repo.create(any(), any(), any(), any(), EmployeeType.HOURLY) }
                verify(exactly = 1) { repo.create(any(), any(), any(), any(), EmployeeType.SALARY) }
                verify(exactly = 0) { repo.create(any(), any(), any(), any(), EmployeeType.COMMISSION) }
            }
        }

        describe ("when type is Commission employee") {
            justRun { repo.create(any(), any(), any(), any(), EmployeeType.COMMISSION) }
            it ("add salary employee") {
                AddEmp(repo, listOf("1", "name", "address", "C", "hoge")).exec()
                verify(exactly = 0) { repo.create(any(), any(), any(), any(), EmployeeType.HOURLY) }
                verify(exactly = 0) { repo.create(any(), any(), any(), any(), EmployeeType.SALARY) }
                verify(exactly = 1) { repo.create(any(), any(), any(), any(), EmployeeType.COMMISSION) }
            }
        }

        describe ("when type is invalid") {
            it ("throw exception") {
                shouldThrow<IllegalArgumentException> {
                    AddEmp(repo, listOf("1", "name", "address", "X", "hoge")).exec()
                }
            }
        }

        describe ("when duplicate entry") {
            it ("throw exception") {
                every { repo.findByID(any()) } returns mockk {
                    every { name } returns "hoge"
                }
                shouldThrow<Exception> {
                    AddEmp(repo, listOf("1", "fuga", "address", "H", "hoge")).exec()
                }
            }
        }
    }
})