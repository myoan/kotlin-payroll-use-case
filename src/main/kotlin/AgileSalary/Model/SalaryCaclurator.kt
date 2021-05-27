package AgileSalary.Model

import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object SalaryCaclurator {
    fun calcurate(employee: Employee): Int {
        var salary = when(employee.type) {
            EmployeeType.HOURLY.index -> {
                val tcs = transaction {
                    addLogger(StdOutSqlLogger)
                    TimeCards
                        .slice(TimeCards.columns)
                        .select {
                            (TimeCards.empID eq employee.id.value).and(TimeCards.createdAt greaterEq employee.lastPayday)
                        }.toList()
                }
                val sum = tcs.map { it.get(TimeCards.workingTime) }
                    .sumOf {
                        if ((it - 8) <= 0) it.toDouble() else (it - 8) * 1.5 + 8
                    }
                return (employee.data1.toInt() * sum).toInt()
            }
            EmployeeType.SALARY.index -> 0
            EmployeeType.COMMISSION.index -> 0
            else -> 0
        }

        if (employee.isUnion()) {
            salary -= UnionServiceCharge.FEE * 4
        }
        return salary
    }
}