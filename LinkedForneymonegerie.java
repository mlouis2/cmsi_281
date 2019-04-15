package linked_forneymonegerie;

import java.util.NoSuchElementException;

public class LinkedForneymonegerie implements LinkedForneymonegerieInterface {

    private ForneymonType head;
    private int size, typeSize, modCount;
    
    LinkedForneymonegerie () {
    	head = null;
        size = 0;
        typeSize = 0;
        modCount = 0;
    }
    
    public boolean empty () {
        return size == 0;
    }
    
    public int size () {
        return size;
    }
    
    public int typeSize () {
        return typeSize;
    }
    
    public boolean collect (String toAdd) {
    	boolean notAlreadyInList = true;
    	ForneymonType foundType = findType(toAdd);
    	if (foundType == null) {
    		appendTypeToEnd(toAdd, 1);
    	} else {
    		foundType.count++;
    		notAlreadyInList = false;
    		size++;
    	}
    	modCount++;
    	return notAlreadyInList;
    }
    
    public boolean release (String toRemove) {
        ForneymonType typeToRemove = findType(toRemove);
        if (typeToRemove == null) { return false; }
        if (typeToRemove.count == 1) {
        	releaseType(toRemove);
        } else {
        	typeToRemove.count--;
        	size--;
        }
        modCount++;
        return true;
    }
    
    public void releaseType (String toNuke) {
    	ForneymonType typeToRemove = findType(toNuke);
    	removeReferencesFromType(typeToRemove);
    	typeSize--;
    	size -= typeToRemove.count;
    }
    
    public int countType (String toCount) {
    	ForneymonType typeFound = findType(toCount);
    	if (typeFound == null) { return 0; }
        return typeFound.count;
    }
    
    public boolean contains (String toCheck) {
        return findType(toCheck) != null;
    }
    
    public String rarestType () {
    	if (empty()) { return null; }
    	ForneymonType current = head;
    	String currentRarestType = current.type;
    	int rarest = current.count;
    	do  {
    		if (current.count <= rarest) {
    			rarest = current.count;
    			currentRarestType = current.type;
    		}
    		current = current.next;
    	} while (current != null);
    	return currentRarestType;
    }
    
    public LinkedForneymonegerie clone () {
    	LinkedForneymonegerie newFm = new LinkedForneymonegerie();
    	if (empty()) { return newFm; }
    	ForneymonType current = head;
    	do  {
    		newFm.appendTypeToEnd(current.type, current.count);
    		current = current.next;
    	} while (current != null);
    	return newFm;
    }
    
    public void trade (LinkedForneymonegerie other) {
    	ForneymonType tempHead = head;
    	int tempSize = size;
    	int tempTypeSize = typeSize;
    	int tempModCount = modCount;
    	
    	head = other.head;
    	size = other.size;
    	typeSize = other.typeSize;
    	modCount = other.modCount;
    	
    	other.head = tempHead;
    	other.size = tempSize;
    	other.typeSize = tempTypeSize;
    	other.modCount = tempModCount;
    }
    
    public LinkedForneymonegerie.Iterator getIterator () {
    	if (empty()) { throw new IllegalStateException(); }
        return new Iterator(this);
    }
    
    public String toString() {
    	String result = "[ ";
    	if (empty()) { return result + " ]"; }
    	ForneymonType current = head;
    	while (current.next != null) {
    		result += current + ", ";
    		current = current.next;
    	}
    	return result += current + " ]";
    }
    
    public static LinkedForneymonegerie diffMon (LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
    	LinkedForneymonegerie diffFm = y1.clone();
    	ForneymonType y1Current = diffFm.head;
    	ForneymonType y2Current;
    	if (y2.empty() || y2.empty()) { return diffFm; }
    	do {
    		y2Current = y2.findType(y1Current.type);
    		removeDifferenceInCounts(diffFm, y1Current, y2Current);
    		y1Current = y1Current.next;
    	} while (y1Current != null);
    	return diffFm;
    }
    
    public static boolean sameCollection (LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
    	return (diffMon(y1, y2).empty() && (y1.typeSize == y2.typeSize && y1.size == y2.size));
    }
    
    private ForneymonType findType(String type) {
    	if (empty()) { return null; }
    	ForneymonType current = head;
    	do  {
    		if (current.type.equals(type)) {
    			return current;
    		}
    		current = current.next;
    	} while (current != null);
    	return null;
    }
    
    private ForneymonType findLast() {
    	if (empty()) { return null; }
    	ForneymonType current = head;
    	while (current.next != null) {
    		current = current.next;
    	}
    	return current;
    }
    
    private void appendTypeToEnd(String type, int count) {
    	if (empty()) { 
    		head = new ForneymonType(type, count);
    		typeSize++;
    		size = count;
    	} else {
    		ForneymonType lastInList = findLast();
        	lastInList.next = new ForneymonType(type, count);
        	lastInList.next.prev = lastInList;
        	typeSize++;
        	size += count;
    	}
    }
    
    private void removeReferencesFromType(ForneymonType typeToRemove) {
		if (head == typeToRemove) {
    		head = typeToRemove.next;
    	} else if (typeToRemove.next == null) {
    		typeToRemove.prev.next = null;
    	} else {
    		typeToRemove.prev.next = typeToRemove.next;
    		typeToRemove.next.prev = typeToRemove.prev;
    	}
	}
    
    private static int differenceBetweenCounts(ForneymonType y1Type, ForneymonType y2Type) {
    	return y1Type.count - y2Type.count;
    }
    
    private static void removeDifferenceInCounts(LinkedForneymonegerie diffFm, ForneymonType y1Current,
			ForneymonType y2Current) {
    	if (y2Current == null) { return; }
		int difference = differenceBetweenCounts(y1Current, y2Current);
		if (difference == 0) {
			diffFm.releaseType(y1Current.type);
		} else {
			y1Current.count -= difference;
		}
	}
    
    public class Iterator implements LinkedForneymonegerieIteratorInterface {
        LinkedForneymonegerie owner;
        ForneymonType current;
        int itModCount;
        int indexInCurrentType;
        
        Iterator (LinkedForneymonegerie y) {
            owner = y;
            current = owner.head;
            itModCount = owner.modCount;
            indexInCurrentType = 0;
        }
        
        public boolean hasNext () {
            if (current == null) { return false; }
            if (indexInCurrentType == current.count - 1) {
            	return current.next != null;
            }
            return true;
        }
        
        public boolean hasPrev () {
        	if (current == null) { return false; }
        	if (indexInCurrentType == 0) {
            	return current.prev != null;
            }
            return true;
        }
        
        public boolean isValid () {
            return itModCount == owner.modCount;
        }
        
        public String getType () {
            return current.type;
        } 

        public void next () {
            if (!hasNext()) { throw new NoSuchElementException(); }
            checkIfValid();
            if (indexInCurrentType == current.count - 1) {
            	current = current.next;
            	indexInCurrentType = 0;
            } else {
            	indexInCurrentType++;
            }
        }
        
        public void prev () {
        	if (!hasPrev()) { throw new NoSuchElementException(); }
            checkIfValid();
            if (indexInCurrentType == 0) {
            	current = current.prev;
            	indexInCurrentType = current.count - 1;
            } else {
            	indexInCurrentType--;
            }
        }
        
        public void replaceAll (String toReplaceWith) {
        	checkIfValid();
        	ForneymonType typeToReplaceWith = owner.findType(toReplaceWith);
        	if (typeToReplaceWith == null) {
        		removeOldTypeAddNewType(toReplaceWith);
        	} else if (!typeToReplaceWith.type.equals(this.getType())){
        		combineCountsOfTypes(typeToReplaceWith);
        	}
        	itModCount++;
        	owner.modCount++;
        }

		private void removeOldTypeAddNewType(String toReplaceWith) {
			int count = current.count;
			owner.removeReferencesFromType(current);
			ForneymonType lastType = owner.findLast();
			lastType.next = new ForneymonType(toReplaceWith, count);
			current = lastType.next;
		}

		private void combineCountsOfTypes(ForneymonType typeToReplaceWith) {
			owner.removeReferencesFromType(current);
			typeToReplaceWith.count += current.count;
			current = typeToReplaceWith;
		}
        
        private void checkIfValid() {
        	if (!isValid()) { throw new IllegalStateException(); }
        }
        
    }
    
    private class ForneymonType {
        ForneymonType next, prev;
        String type;
        int count;
        
        ForneymonType (String t, int c) {
            type = t;
            count = c;
        }
        
        public String toString() {
        	return ("\"" + type + "\": " + count);
        }
    }
    
}