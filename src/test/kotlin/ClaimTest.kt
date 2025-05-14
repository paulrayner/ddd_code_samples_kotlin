package warranty

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate
import java.util.*

class ClaimTest {
    @Test
    fun claimIsSetupCorrectly() {
        val lineItem1 = LineItem("PARTS", 45.0, "Replacement part for soap dispenser")
        val lineItem2 = LineItem("LABOR", 50.0, "1 hour repair")
        val repairPO = RepairPO()
        repairPO.lineItems.add(lineItem1)
        repairPO.lineItems.add(lineItem2)

        val claim = Claim(100.0, LocalDate.of(2010, 5, 8))
        claim.repairPO.add(repairPO)

        assertEquals(100.0, claim.amount)
        assertEquals(LocalDate.of(2010, 5, 8), claim.failureDate)
        assertEquals("PARTS", claim.repairPO[0].lineItems[0].type)
        assertEquals(45.0, claim.repairPO[0].lineItems[0].amount)
        assertEquals("Replacement part for soap dispenser", claim.repairPO[0].lineItems[0].description)
        assertEquals("LABOR", claim.repairPO[0].lineItems[1].type)
        assertEquals(50.0, claim.repairPO[0].lineItems[1].amount)
        assertEquals("1 hour repair", claim.repairPO[0].lineItems[1].description)
    }

    // Entities compare by unique IDs, not properties
    @Test
    fun claimEquality() {
        val claim1 = Claim(100.0, LocalDate.of(2010, 5, 8))
        val claim2 = Claim(100.0, LocalDate.of(2010, 5, 8))
        val claim3 = Claim(100.0, LocalDate.of(2010, 5, 8))
        val expectedId = UUID.randomUUID()

        claim1.id = expectedId
        claim2.id = expectedId
        assertEquals(claim1, claim2)

        assertNotEquals(claim1, claim3)
    }
}