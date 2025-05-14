package warranty
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ProductTest {

    // Product is an example of a value object. See https://martinfowler.com/bliki/ValueObject.html for more details
    @Test
    fun productEquality() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        assertEquals(Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0"), product)
    }

    // Product is an example of a value object. See https://martinfowler.com/bliki/ValueObject.html for more details
    @Test
    fun productInequality() {
        val product = Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0")
        assertNotEquals(Product("stove", "OEUOEU23", "Whirlpool", "7DP840CWDB0"), product)
        assertNotEquals(Product("stove", "BEUOEU23", "Whirlpool", "7DP840CWDB0"), product)
        assertNotEquals(Product("stove", "OEUOEU23", "Maytag", "7DP840CWDB0"), product)
        assertNotEquals(Product("stove", "OEUOEU23", "Whirlpool", "9999999"), product)
    }
}