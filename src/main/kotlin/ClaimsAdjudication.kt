package warranty

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
        val claimTotal = contract.claims.sumOf { it.amount }
        if (((contract.purchasePrice - claimTotal) * 0.8 > newClaim.amount) &&
            (contract.status == Contract.Status.ACTIVE) &&
            (newClaim.failureDate >= contract.effectiveDate) &&
            (newClaim.failureDate <= contract.expirationDate)) {
            contract.add(newClaim)
        }
    }
}