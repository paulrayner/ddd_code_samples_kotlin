package warranty

import java.time.LocalDate

/**
 * Adjudicate/adjudication - a judgment made on a claim to determine whether
 * we are legally obligated to process the claim against the warranty. From
 * Wikipedia (https://en.wikipedia.org/wiki/Adjudication):
 * "Claims adjudication" is a phrase used in the insurance industry to refer to
 * the process of paying claims submitted or denying them after comparing claims
 * to the benefit or coverage requirements.
 */

class ClaimsAdjudication {
    fun adjudicate(contract: Contract, newClaim: Claim) {
        if ((limitOfLiability(contract) > newClaim.amount) &&
            (inEffectFor(contract, newClaim.failureDate))) {
            contract.add(newClaim)
        }
    }

    fun limitOfLiability(contract: Contract): Double {
        val claimTotal = contract.claims.sumOf { it.amount }
        return (contract.purchasePrice - claimTotal) * 0.8;
    }

    fun inEffectFor(contract: Contract, failureDate: LocalDate): Boolean {
        return (contract.status == Contract.Status.ACTIVE) &&
                (failureDate >= contract.effectiveDate) &&
                (failureDate <= contract.expirationDate);
    }
}