package warranty

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.util.*


class ContractTest {
    @Test
    fun contractIsSetupCorrectly() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        val contract = Contract(100.0, product, LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))

        assertNotNull(contract.id)
        assertEquals(100.0, contract.purchasePrice)
        assertEquals(Contract.Status.PENDING, contract.status)
        assertEquals(contract.purchaseDate, LocalDate.of(2010, 5, 7))
        assertEquals(contract.effectiveDate, LocalDate.of(2010, 5, 8))
        assertEquals(contract.expirationDate, LocalDate.of(2013, 5, 8))
        assertEquals(product, contract.coveredProduct)
    }

    // Entities compare by unique IDs, not properties
    @Test
    fun contractEquality() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        val contract1 = Contract(100.0, product, LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        val contract2 = Contract(100.0, product, LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        val contract3 = Contract(100.0, product, LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))

        val expectedId = UUID.randomUUID()
        contract1.id = expectedId
        contract2.id = expectedId

        assertEquals(contract2, contract1)
        assertNotEquals(contract3, contract1)
    }
}