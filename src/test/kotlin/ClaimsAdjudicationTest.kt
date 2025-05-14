package warranty

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ClaimsAdjudicationTest {
    fun fakeContract(): Contract {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        val contract = Contract(100.0, product, LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        contract.status = Contract.Status.ACTIVE

        return contract
    }

    @Test
    fun adjudicateValidClaim() {
        val contract = fakeContract()
        val claim = Claim(79.0, LocalDate.of(2010, 5, 8))

        ClaimsAdjudication().adjudicate(contract, claim)

        assertEquals(1, contract.claims.size)
        assertEquals(79.0, contract.claims[0].amount)
        assertEquals(LocalDate.of(2010, 5, 8), contract.claims[0].failureDate)
    }

    @Test
    fun adjudicateClaimWithInvalidAmount() {
        val contract: Contract = fakeContract()
        val claim = Claim(81.0, LocalDate.of(2010, 5, 8))

        ClaimsAdjudication().adjudicate(contract, claim)

        assertEquals(0, contract.claims.size)
    }

    @Test
    fun adjudicateClaimForPendingContract() {
        val contract: Contract = fakeContract()
        contract.status = Contract.Status.PENDING
        val claim = Claim(79.0, LocalDate.of(2010, 5, 8))

        ClaimsAdjudication().adjudicate(contract, claim)

        assertEquals(0, contract.claims.size)
    }

    @Test
    fun adjudicateClaimForExpiredContract() {
        val contract: Contract = fakeContract()
        contract.status = Contract.Status.EXPIRED
        val claim = Claim(79.0, LocalDate.of(2010, 5, 8))

        ClaimsAdjudication().adjudicate(contract, claim)

        assertEquals(0, contract.claims.size)
    }

    @Test
    fun adjudicateClaimPriorToEffectiveDate() {
        val contract: Contract = fakeContract()
        val claim = Claim(79.0, LocalDate.of(2010, 5, 7))

        ClaimsAdjudication().adjudicate(contract, claim)

        assertEquals(0, contract.claims.size)
    }

    @Test
    fun adjudicateClaimAfterToExpirationDate() {
        val contract: Contract = fakeContract()
        val claim = Claim(79.0, LocalDate.of(2013, 5, 9))

        ClaimsAdjudication().adjudicate(contract, claim)

        assertEquals(0, contract.claims.size)
    }
}