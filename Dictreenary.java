package dictreenary;

import java.util.ArrayList;

public class Dictreenary implements DictreenaryInterface {

    TTNode root;
    
    Dictreenary () {}
    
    public boolean isEmpty () {
    	return (root == null);
    }
    
    public void addWord (String toAdd) {
    	root = TTNode.addLetter(root, normalizeWord(toAdd));
    }
    
    public boolean hasWord (String query) {
    	if (root == null) { return false; }
    	return TTNode.hasWord(root, normalizeWord(query));
    }
    
    public String spellCheck (String query) {
    	query = normalizeWord(query);
    	if (this.hasWord(query)) {
    		return query;
    	}
    	String changeToCheck = "";
    	for (int i = 0; i < query.length() - 1; i++) {
    		changeToCheck = query.substring(0, i) + query.charAt(i + 1) + query.charAt(i) + query.substring(i + 2, query.length());
    		if (this.hasWord(changeToCheck)) {
    			return changeToCheck;
    		}
    	}
        return null;
    }
    
    public ArrayList<String> getSortedWords () {
        return TTNode.getSortedWords(root, "", new ArrayList<String>());
    }
    
    private String normalizeWord (String s) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }
    
    private static int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }
    
    private static class TTNode {
        
        boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        
        TTNode (char c, boolean w) {
            letter  = c;
            wordEnd = w;
        }
        
        static TTNode addLetter(TTNode node, String toAdd) {
        	if (toAdd.length() == 0) { return null; }
        	if (node == null) {
        		node = new TTNode(toAdd.charAt(0), toAdd.length() == 1);
        	}
        	int compareValue = compareChars(toAdd.charAt(0), node.letter);
        	if (compareValue == 0) {
        		if (toAdd.length() == 1) {
        			node.wordEnd = true;
        		} else {
        			node.mid = addLetter(node.mid, toAdd.substring(1));
        		}
        	} else if (compareValue < 0) {
        		node.left = addLetter(node.left, toAdd);
        	} else {
        		node.right = addLetter(node.right, toAdd);
        	}
        	return node;
        }
        
        static boolean hasWord(TTNode node, String query) {
        	if (node == null) { return false; }
        	int compareValue = compareChars(query.charAt(0), node.letter);
        	if (compareValue == 0) {
        		if (query.length() == 1) {
        			return node.wordEnd;
        		}
        		return hasWord(node.mid, query.substring(1));
        	} else if (compareValue < 0) {
        		return hasWord(node.left, query);
        	} else {
        		return hasWord(node.right, query);
        	}
        }
        
        static ArrayList<String> getSortedWords(TTNode node, String wordSoFar, ArrayList<String> wordList) {
        	if (node == null) { return wordList; }
        	wordList = getSortedWords(node.left, wordSoFar, wordList);
        	if (node.wordEnd) {
        		wordList.add(wordSoFar + node.letter);
        	}
        	wordList = getSortedWords(node.mid, wordSoFar + node.letter, wordList);
        	wordList = getSortedWords(node.right, wordSoFar, wordList);
        	return wordList;
        }
    }
    
}