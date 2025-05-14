package warranty

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class TermsAndConditionsTest {
    @Test
    fun termsAndConditionsStatus() {
        val termsAndConditions = TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))

        // Should be pending prior to effective date
        assertEquals(Contract.Status.PENDING, termsAndConditions.status(LocalDate.of(2010, 5, 7)))

        // Should be active if between effective and expiration dates (inclusively)
        assertEquals(Contract.Status.ACTIVE, termsAndConditions.status(LocalDate.of(2010, 5, 8)))
        assertEquals(Contract.Status.ACTIVE, termsAndConditions.status(LocalDate.of(2013, 5, 8)))

        // Should be expired after to expiration date
        assertEquals(Contract.Status.EXPIRED, termsAndConditions.status(LocalDate.of(2013, 5, 9)))
    }

    // Moved from Contract
    @Test
    fun termsAndConditionsLimitOfLiability() {
        val termsAndConditions = TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))
        assertEquals(80.0, termsAndConditions.limitOfLiability(100.0))
    }

    @Test
    fun termsAndConditionsExtendAnnually() {
        val termsAndConditions = TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))

        val extendedTermsAndConditions = TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2014, 5, 8))

        assertEquals(extendedTermsAndConditions, termsAndConditions.annuallyExtended())
    }

    // Could write tests to prevent invalid states
    /*
    @Test
    fun TestTermsAndConditionsRejectsInvalidDates() {
        assertThrows(Exception::class.java, {
            TermsAndConditions(LocalDate.of(2010, 5, 9), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8));
          });
    }*/

    // TermsAndConditions is an example of a value object. See https://martinfowler.com/bliki/ValueObject.html for more details
    @Test
    fun termsAndConditionsEquality() {
        // A value object must be created whole
        val termsAndConditions = TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))

        assertEquals(TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8)), termsAndConditions)
    }

    // TermsAndConditions is an example of a value object. See https://martinfowler.com/bliki/ValueObject.html for more details
    @Test
    fun termsAndConditionsInequality() {
        val termsAndConditions = TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8))

        assertNotEquals(TermsAndConditions(LocalDate.of(2010, 5, 6), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 8)), termsAndConditions)
        assertNotEquals(TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 5), LocalDate.of(2013, 5, 8)), termsAndConditions)
        assertNotEquals(TermsAndConditions(LocalDate.of(2010, 5, 7), LocalDate.of(2010, 5, 8), LocalDate.of(2013, 5, 4)), termsAndConditions)
    }
}