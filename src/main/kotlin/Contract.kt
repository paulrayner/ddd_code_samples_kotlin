package warranty

import java.time.LocalDate
import java.util.*

/**
 * Contract represents an extended warranty for a covered product.
 * A contract is in a PENDING state prior to the effective date,
 * ACTIVE between effective and expiration dates, and EXPIRED after
 * the expiration date.
 */
class Contract(var purchasePrice: Double, var coveredProduct: Product, var termsAndConditions: TermsAndConditions) {
    enum class Status {
        PENDING, ACTIVE, EXPIRED
    }

    var id: UUID = UUID.randomUUID()
    var status: Status = Status.PENDING

    val claims: MutableList<Claim> = mutableListOf()

    var events: MutableList<ContractEvent> = mutableListOf()

    fun add(claim: Claim) {
        claims.add(claim)
    }

    fun covers(claim: Claim): Boolean = inEffectFor(claim.failureDate) && withinLimitOfLiability(claim.amount)

    fun inEffectFor(failureDate: LocalDate): Boolean =
        termsAndConditions.status(failureDate) == Status.ACTIVE && status == Status.ACTIVE

    fun withinLimitOfLiability(claimAmount: Double): Boolean = claimAmount < remainingLiability()

    fun remainingLiability() = termsAndConditions.limitOfLiability(purchasePrice) - claimTotal()

    fun claimTotal(): Double = claims.sumOf { it.amount }

    fun extendAnnualSubscription() {
        termsAndConditions = termsAndConditions.annuallyExtended()
        events.add(SubscriptionRenewed(this.id, "Automatic Annual Renewal"))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contract

        return id == other.id
    }
}