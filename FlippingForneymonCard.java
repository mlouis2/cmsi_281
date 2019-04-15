//Maddie Louis, Sage Strieker, Shanaya Nagendran, Lauren Lindsey

package forneymon.cardgame;

public class FlippingForneymonCard extends ForneymonCard {
	public boolean isFaceDown;
	
	FlippingForneymonCard(String n, String t, boolean isFlippedDown) {
		super(n, t);
		isFaceDown = isFlippedDown;
	}
	
	FlippingForneymonCard() {
		super();
		isFaceDown = true;
	}
	
	public boolean flip() {
		isFaceDown = !isFaceDown;
		return isFaceDown;
	}
	
	public int match(FlippingForneymonCard other) {
		if (isFaceDown || other.isFaceDown) {
			return 2;
		} else if (this.equals(other)) {
			return 1;
		} else {
			return 0;
		}	
	}
	
	public boolean equals(FlippingForneymonCard other) {
		return (this.getName().equals(other.getName()) && (this.getType().equals(other.getType())));
	}
	
	public String toString() {
		if (isFaceDown) {
			return ("?: ?");
		} else {
			return super.toString();
		}
	}
}
