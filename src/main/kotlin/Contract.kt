package warranty

import java.time.LocalDate
import java.util.*

/**
 * Contract represents an extended warranty for a covered product.
 * A contract is in a PENDING state prior to the effective date,
 * ACTIVE between effective and expiration dates, and EXPIRED after
 * the expiration date.
 */
class Contract(var purchasePrice: Double, var coveredProduct: Product, var purchaseDate: LocalDate, var effectiveDate: LocalDate, var expirationDate: LocalDate) {
    enum class Status {
        PENDING, ACTIVE, EXPIRED
    }
    var id : UUID = UUID.randomUUID()
    var status : Status = Status.PENDING

    val claims: MutableList<Claim> = mutableListOf()

    fun add(claim: Claim) {
        claims.add(claim)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contract

        return id == other.id
    }

}