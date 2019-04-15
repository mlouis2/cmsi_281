//Maddie Louis

package sentinal;

public class PhraseHash implements PhraseHashInterface {

    private final static int BUCKET_START = 1000;
    private final static double LOAD_MAX = 0.7;
    private int size, longest;
    private String[] buckets;

    PhraseHash () {
        buckets = new String[BUCKET_START];
        size = 0;
        longest = 0;
    }

    public int size () {
        return size;
    }

    public boolean isEmpty () {
        return size == 0;
    }

    public void put (String s) {
        if (this.get(s) != null) { return; }
        checkIfNewLongest(s);
        int index = hash(s);
        index = checkForCollision(index);
        buckets[index] = s;
        size++;
        checkAndGrow();
    }

    public String get (String s) {
        int index = hash(s);
        int origIndex = index;
        if (buckets[index] == null || !buckets[index].equals(s)) {
        	index++;
        	while (index != origIndex) {
        		if (foundThePhrase(s, index)) {
        			return s;
        		}
        		index++;
        		if (index == buckets.length) {
        			index = 0;
        		}
        	} 	
        } else {
        	return s;
        }
        return null;
    }

    public int longestLength () {
        return longest;
    }

    private int hash (String s) {
    	int val = 1;
    	for (int i = 0; i < s.length() && i < 5; i++) {
    		val *= (int)(s.charAt(i));
    	}
    	return Math.abs(val % buckets.length);
    }

    private void checkAndGrow () {
        double loadFactor = size / buckets.length;
        if (loadFactor <= LOAD_MAX) { return; }
        String[] oldBuckets = buckets;
        buckets = new String[buckets.length * 2];
        for (int i = 0; i < oldBuckets.length; i++) {
        	if (oldBuckets[i] != null) {
        		put(oldBuckets[i]);
        	}
        }
    }
    
    private void checkIfNewLongest(String s) {
		int phraseSize = s.split(" ").length;
        if (phraseSize > longest) {
        	longest = phraseSize;
        }
	}
    
    private int checkForCollision(int index) {
		while (buckets[index] != null) {
        	index++;
        	if (index == buckets.length) {
        		index = 0;
        	}
        }
		return index;
	}
    
    private boolean foundThePhrase(String s, int index) {
    	return (buckets[index] != null && buckets[index].equals(s));
    }
}
