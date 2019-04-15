//Maddie Louis, Sage Strieker, Shanaya Nagendran, Lauren Lindsey

package forneymon.cardgame;

public class ForneymonCard {
	
	private String name;
	private String type;
	public String[] possibleTypes = {"Burnymon", "Dampymon", "Leafymon"};
	
	
	ForneymonCard(String n, String t) {
		boolean isPossibleType = false;
		if (n.equals("")) {
			throw new IllegalArgumentException();
		}
		for (String type : possibleTypes) {
			if (type.equals(t)) {
				isPossibleType = true;
				break;
			}
		}
		if (!isPossibleType) {
			throw new IllegalArgumentException();
		}
		
		name = n;
		type = t;
	}
	
	ForneymonCard() {
		name = "MissingNu";
		type = "Burnymon";
	}
	
	public String toString() {
		return (type + ": " + name);
	}
	
	public String getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
}
