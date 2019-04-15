//Maddie Louis
package forneymonegerie;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class Forneymonegerie implements ForneymonegerieInterface {

	private ForneymonType[] collection;
	private int size;
	private int typeSize;
	private static final int START_SIZE = 16;

	public Forneymonegerie() {
		collection = new ForneymonType[START_SIZE];
		size = 0;
		typeSize = 0;
	}

	public boolean empty() {
		return size == 0;
	}

	public int size() {
    	return size;
    }

	public int typeSize() {
    	return typeSize;
    }

	public boolean collect(String typeToAdd) {
    	if (contains(typeToAdd)) {
    		int index = findIndexOfType(typeToAdd);
    		collection[index].count++;
    		size++;
    		return false;
    	} else {
    		if (typeSize + 1 > collection.length) {
        		grow();
        	}
    		collection[typeSize] = new ForneymonType(typeToAdd, 1);
    		typeSize++;
    		size++;
    		return true;
    	}
    }

	private int findIndexOfType(String type) {
		for (int i = 0; i < typeSize; i++) {
			if (collection[i].type.equals(type)) {
				return i;
			}
		}
		return -1;
	}

	private void grow() {
		ForneymonType[] newCollection = new ForneymonType[collection.length * 2];
		for (int i = 0; i < typeSize; i++) {
			newCollection[i] = collection[i];
		}
		collection = newCollection;
	}

	public boolean release(String typeToRelease) {
		int index = findIndexOfType(typeToRelease);
		if (index != -1) {
			removeForneymon(index, typeToRelease);
			return true;
		}
		return false;
    }

	private void removeForneymon(int index, String type) {
		if (countType(type) == 1) {
			shiftLeft(index);
			typeSize--;
		} else {
			collection[index].count--;
		}
		size--;
	}

	private void shiftLeft(int index) {
		for (int i = index; i < typeSize - 1; i++) {
			collection[i] = collection[i + 1];
		}
	}

	public int countType(String typeToCount) {
		int index = findIndexOfType(typeToCount);
		if (index != -1) {
			return collection[index].count;
		}
		return 0;
    }

	public void releaseType(String typeToNuke) {
		int indexOfType = findIndexOfType(typeToNuke);
		if (indexOfType == -1) { return; }
		int countOfType = collection[indexOfType].count;
		shiftLeft(indexOfType);
		typeSize--;
		size -= countOfType;
    }

	public boolean contains(String typeToCheck) {
		return findIndexOfType(typeToCheck) != -1;
    }

	public String nth(int n) {
		if (n < 0 || n >= size) {
			throw new IllegalArgumentException("Argument beyond size");
		}
		int current = 0;
    	for (int i = 0; i < typeSize; i++) {
    		for (int j = 0; j < collection[i].count; j++) {
    			if (current == n) {
    				return collection[i].type;
    			}
    			current++;
    		}
    	}
    	return "";
    }

	public String rarestType() {
		if (typeSize == 0) { return null; }
		int indexOfRarestType = typeSize - 1;
    	int numRarest = collection[indexOfRarestType].count;
    	for (int i = typeSize - 1; i >= 0; i--) {
    		if (collection[i].count < numRarest) {
    			indexOfRarestType = i;
    			numRarest = collection[i].count;
    		}
    	}
    	return collection[indexOfRarestType].type;
    }

	public Forneymonegerie clone() {
		Forneymonegerie clone = new Forneymonegerie();
		while (clone.collection.length < typeSize) {
			clone.grow();
		}
		for (int i = 0; i < typeSize; i++) {
			clone.collection[i] = new ForneymonType(collection[i].type, collection[i].count);
		}
		clone.size = size;
		clone.typeSize = typeSize;
		return clone;
    }

	public void trade(Forneymonegerie other) {
    	ForneymonType[] temp = collection;
    	collection = other.collection;
    	other.collection = temp;

    	int tempSize = size;
    	int tempTypeSize = typeSize;
    	size = other.size;
    	typeSize = other.typeSize;
    	other.size = tempSize;
    	other.typeSize = tempTypeSize;
    }

	public String toString() {
		String result = "[ ";
		for (int i = 0; i < typeSize; i++) {
			result += collection[i].toString();
			if (i != typeSize - 1) {
				result += ", ";
			}
		}
		return result + " ]";
	}

	public static Forneymonegerie diffMon(Forneymonegerie y1, Forneymonegerie y2) {
		Forneymonegerie diffFm = y1.clone();
		int indexInY2;
		for (int i = 0; i < y1.typeSize; i++) {
			indexInY2 = y2.findIndexOfType(diffFm.collection[i].type);
			if (indexInY2 != -1) {
				if (diffFm.collection[i].count > y2.collection[indexInY2].count) {
					diffFm.collection[i].count -= (diffFm.collection[i].count - y2.collection[indexInY2].count);
				} else if (diffFm.collection[i].count == y2.collection[indexInY2].count) {
					diffFm.releaseType(y2.collection[indexInY2].type);
				}
			}
		}
		return diffFm;
		}

	public static boolean sameCollection(Forneymonegerie y1, Forneymonegerie y2) {
		return (diffMon(y1, y2).empty() && (y1.typeSize == y2.typeSize && y1.size == y2.size));
	}

	 private class ForneymonType {
	      String type;
	      int count;

	      ForneymonType (String t, int c) {
	          type = t;
	          count = c;
	      }

	      public String toString() {
	    	  return "\"" + type + "\"" + ": " + count;
	      }
	  }
}
