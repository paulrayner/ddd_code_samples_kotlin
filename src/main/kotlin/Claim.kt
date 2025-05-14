package warranty

import java.time.LocalDate
import java.util.*

class Claim(var amount: Double, var failureDate: LocalDate) {

    var id: UUID = UUID.randomUUID()
    val repairPO: MutableList<RepairPO> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Claim

        return id == other.id
    }

}