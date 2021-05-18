package AgileSalary.Command

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*

class DelEmpSpec: DescribeSpec({
     describe("exec") {
         beforeTest {
             mockkObject(repo)
         }
         describe("when id exists") {
             every { repo.findByID(any()) } returns mockk {
                 every { name } returns "hoge"
             }
             justRun { repo.deleteByID(any()) }
             it("delete employee") {
                 DelEmp(repo, listOf("1")).exec()
                 verify(exactly = 1) { repo.deleteByID(any()) }
             }
        }

        describe ("when id not exist") {
            it ("throw exception") {
                shouldThrow<Exception> {
                    DelEmp(repo, listOf("1")).exec()
                }
            }
        }
    }
})
