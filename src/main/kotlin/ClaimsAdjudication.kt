package warranty

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