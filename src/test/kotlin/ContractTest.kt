package warranty

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*


class ContractTest {
    @Test
    fun contractIsSetupCorrectly() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        val termsAndConditions =
            TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        val contract = Contract(100.0, product, termsAndConditions)

        assertNotNull(contract.id)
        assertEquals(100.0, contract.purchasePrice)
        assertEquals(Contract.Status.PENDING, contract.status)
        assertEquals(contract.termsAndConditions, termsAndConditions)
        assertEquals(product, contract.coveredProduct)
    }

    @Test
    fun contractInEffectBasedOnStatusAndEffectiveAndExpirationDateRange() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        val termsAndConditions =
            TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        val contract = Contract(100.0, product, termsAndConditions)

        contract.status = Contract.Status.PENDING
        assertFalse(contract.inEffectFor(LocalDate.of(2010, 5, 9)))

        contract.status = Contract.Status.ACTIVE
        assertFalse(contract.inEffectFor(LocalDate.of(2010, 5, 7)))
        assertTrue(contract.inEffectFor(LocalDate.of(2010, 5, 8)))
        assertTrue(contract.inEffectFor(LocalDate.of(2013, 5, 7)))
        assertFalse(contract.inEffectFor(LocalDate.of(2013, 5, 9)))

        contract.status = Contract.Status.EXPIRED
        assertFalse(contract.inEffectFor(LocalDate.of(2010, 5, 8)))
    }

    @Test
    fun claimTotalSumOfClaimAmounts() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        val termsAndConditions =
            TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        val contract = Contract(100.0, product, termsAndConditions)

        contract.add(Claim(10.0, LocalDate.of(2010, 10, 1)))
        contract.add(Claim(20.0, LocalDate.of(2010, 10, 1)))

        assertEquals(30.0, contract.claimTotal())
    }

    @Test
    fun claimAmountsWithinLimitOfLiability() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        val termsAndConditions =
            TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        val contract = Contract(100.0, product, termsAndConditions)

        assertTrue(contract.withinLimitOfLiability(10.0))
        assertTrue(contract.withinLimitOfLiability(79.0))
        assertFalse(contract.withinLimitOfLiability(80.0)) // Must be less than the limit amount
        assertFalse(contract.withinLimitOfLiability(90.0))
    }

    @Test
    fun activeContractCoverage() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        val termsAndConditions =
            TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        val contract = Contract(100.0, product, termsAndConditions)

        contract.status = Contract.Status.PENDING
        assertFalse(contract.covers(Claim(10.0, LocalDate.of(2010, 10, 1))))

        contract.status = Contract.Status.ACTIVE
        assertTrue(contract.covers(Claim(10.0, LocalDate.of(2010, 10, 1))))
        assertTrue(contract.covers(Claim(79.0, LocalDate.of(2010, 10, 1))))
        assertFalse(contract.covers(Claim(80.0, LocalDate.of(2010, 10, 1))))
        assertFalse(contract.covers(Claim(90.0, LocalDate.of(2010, 10, 1))))

        contract.status = Contract.Status.EXPIRED
        assertFalse(contract.covers(Claim(10.0, LocalDate.of(2010, 10, 1))))
    }

    @Test
    fun extendAnnualSubscription() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        val termsAndConditions =
            TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        val contract = Contract(100.0, product, termsAndConditions)

        contract.extendAnnualSubscription()
        val extendedTermsAndConditions =
            TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2014, 5, 8))

        assertEquals(extendedTermsAndConditions, contract.termsAndConditions)
        assertEquals(1, contract.events.size)
        assertTrue(contract.events[0] is SubscriptionRenewed)
        assertEquals(contract.id, (contract.events[0] as SubscriptionRenewed).contractId)
        assertEquals("Automatic Annual Renewal", (contract.events[0] as SubscriptionRenewed).reason)
    }

    // TODO: Practice using domain events by making this test pass
//    @Test
//    fun terminateContract() {
//        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
//        val termsAndConditions = TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
//        val contract = Contract(100.0, product, termsAndConditions)
//
//        contract.terminate("Debbie", "Limit of Liability Exceeded")
//
//        assertEquals(Contract.Status.FULFILLED, contract.status)
//        assertEquals(1, contract.events.size)
//        assertTrue(contract.events[0] is CustomerReimbursementRequested)
//        assertEquals(contract.id, (contract.events[0] as CustomerReimbursementRequested).contractId)
//        assertEquals("Debbie", (contract.events[0] as CustomerReimbursementRequested).repName)
//        assertEquals("Limit of Liability Exceeded", (contract.events[0] as CustomerReimbursementRequested).reason)
//    }

    // Entities compare by unique IDs, not properties
    @Test
    fun contractEquality() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        val termsAndConditions =
            TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        val contract1 = Contract(100.0, product, termsAndConditions)
        val contract2 = Contract(100.0, product, termsAndConditions)
        val contract3 = Contract(100.0, product, termsAndConditions)

        val expectedId = UUID.randomUUID()
        contract1.id = expectedId
        contract2.id = expectedId

        assertEquals(contract2, contract1)
        assertNotEquals(contract3, contract1)
    }
}