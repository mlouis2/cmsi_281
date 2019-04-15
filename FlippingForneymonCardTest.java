//Maddie Louis, Sage Strieker, Shanaya Nagendran, Lauren Lindsey

package forneymon.cardgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FlippingForneymonCardTest {

	@Test
	void testToString() {
		FlippingForneymonCard bob = new FlippingForneymonCard("Bob", "Burnymon", false);
		assertEquals("Burnymon: Bob", bob.toString());
	}
	
	@Test
	void testFlip() {
		FlippingForneymonCard bob = new FlippingForneymonCard("Bob", "Burnymon", true);
		bob.flip();
		assertEquals(bob.isFaceDown, false);
	}
	
	@Test
	void testDefaultNaming() {
		FlippingForneymonCard missingNu = new FlippingForneymonCard();
		assertEquals("MissingNu", missingNu.getName());
	}
}
