package warranty

import java.time.LocalDate

/**
 * Terms and Conditions represents the legal obligations for the contract, such as the coverage period
 * based on the effective and expiration dates, the limit of liability, and how to handle extensions on
 * subscription-based warranties.
 */
data class TermsAndConditions(
    val purchaseDate: LocalDate,
    val effectiveDate: LocalDate,
    val expirationDate: LocalDate
) {
    init {
        // Could add validation logic here to throw exceptions for invalid terms and conditions states,
        // such as throwing exceptions when dates are out of sequence
        if ((purchaseDate > effectiveDate) ||
            (effectiveDate > expirationDate)
        ) {
            // Perhaps throw an exception here...
        }
    }

    fun status(date: LocalDate): Contract.Status {
        if (date < effectiveDate) return Contract.Status.PENDING
        if (date > expirationDate) return Contract.Status.EXPIRED
        return Contract.Status.ACTIVE
    }

    fun limitOfLiability(purchasePrice: Double): Double {
        val liabilityPercentage = 0.8
        return (purchasePrice * liabilityPercentage)
    }

    fun annuallyExtended(): TermsAndConditions =
        TermsAndConditions(purchaseDate, effectiveDate, expirationDate.plusYears(1))

}
