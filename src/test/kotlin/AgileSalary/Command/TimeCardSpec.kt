package AgileSalary.Command

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.*

class TimeCardSpec: DescribeSpec({
    // val db = MemoryDataStore()
    // describe("exec") {
    //     beforeTest {
    //         db.clear()
    //         AddEmp(db, listOf("1", "hoge", "address", "H", "hoge")).exec()
    //         AddEmp(db, listOf("2", "fuga", "address", "S", "hoge")).exec()
    //         AddEmp(db, listOf("3", "piyo", "address", "C", "hoge", "fuga")).exec()
    //     }
    //     describe ("when id is hourly employee") {
    //         it ("delete employee") {
    //             AddEmp(db, listOf("1", "5/17", "8")).exec()
    //             DelEmp(db, listOf("1")).exec()
    //             db.employeesNum shouldBe 0
    //         }
    //     }

    //     describe ("when id not exist") {
    //         beforeTest{
    //             val args = listOf("1", "name", "address", "H", "hoge")
    //             val cmd = AddEmp(db, args)
    //             cmd.exec()
    //         }
    //         it ("throw exception") {
    //             shouldThrow<Exception> {
    //                 DelEmp(db, listOf("2")).exec()
    //             }
    //         }
    //     }
    // }
})
