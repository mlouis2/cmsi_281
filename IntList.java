package intlist;

public class IntList {

    // Fields
    private int[] items;
    private int   size;
    private static final int START_SIZE = 8;
    
    // Constructor
    IntList () {
        items = new int[START_SIZE];
        size  = 0;
    }

    public int getAt(int index) {
        indexValidityCheck(index);
        return items[index];
    }

    public void append(int toAdd) {
        insertAt(toAdd, size);
    }
    
    public void prepend (int toAdd) {
        insertAt(toAdd, 0);
    }

    public void insertAt (int toAdd, int index) {
        checkAndGrow();
        if (index != size) {
            shiftRight(index);    
        }
        items[index] = toAdd;
        size++;
    }
    
    public void removeAll (int toRemove) {
        for(int i = 0; i < size; i++) {
            if(items[i] == toRemove) {
                removeAt(i);
                i--;
            }
        }
   
    }

    public void removeAt(int index) {
        indexValidityCheck(index);
        shiftLeft(index);
        size--;
    }
    
    private void indexValidityCheck (int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    /*
     * Expands the size of the list whenever it is at
     * capacity
     */
    private void checkAndGrow () {
        // Case: big enough to fit another item, so no
        // need to grow
        if (size < items.length) {
            return;
        }
        
        // Case: we're at capacity and need to grow
        // Step 1: create new, bigger array; we'll
        // double the size of the old one
        int[] newItems = new int[items.length * 2];
        
        // Step 2: copy the items from the old array
        for (int i = 0; i < items.length; i++) {
            newItems[i] = items[i];
        }
        
        // Step 3: update IntList reference
        items = newItems;
    }
    
    /*
     * Shifts all elements to the right of the given
     * index one left
     */
    private void shiftLeft (int index) {
        for (int i = index; i < size-1; i++) {
            items[i] = items[i+1];
        }
    }
    
    
    private void shiftRight (int index) {
        for (int i = size - 1; i >= index; i--) {
            items[i + 1] = items[i];
        }
        
    }
    
    public static void main(String[] args) {
        IntList inty = new IntList();
        inty.prepend(1);
        inty.prepend(2);
        inty.prepend(3);
        System.out.println(inty.getAt(0) == 3);
        inty.insertAt(60, 1);
        System.out.println(inty.getAt(1) == 60);
        inty.insertAt(60, 3);
        inty.removeAll(60);
        inty.append(62);
        IntList inty2 = new IntList();
        inty2.prepend(5);
        inty2.append(5);
        inty2.prepend(5);
        inty2.append(5);
        inty2.removeAll(5);
        for (int i = 0; i < inty2.size; i++) {
        	System.out.print(inty2.getAt(i) + " ");
        }
        inty2.prepend(5);
        inty2.prepend(5);
        inty2.prepend(3);
        inty2.prepend(5);
        System.out.println(inty2.getAt(1) == 3);
    }
    
}