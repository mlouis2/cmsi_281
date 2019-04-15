//Maddie Louis

package sentinal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Sentinal implements SentinalInterface {

    private PhraseHash posHash, negHash;

    Sentinal (String posFile, String negFile) throws FileNotFoundException {
    	posHash = new PhraseHash();
    	negHash = new PhraseHash();
    	this.loadSentimentFile(posFile, true);
    	this.loadSentimentFile(negFile, false);
    }

    public void loadSentiment (String phrase, boolean positive) {
        if (positive) {
        	posHash.put(phrase);
        } else {
        	negHash.put(phrase);
        }
    }

    public void loadSentimentFile (String filename, boolean positive) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
        	loadSentiment(scanner.nextLine(), positive);
        }
        scanner.close();
    }

    public String sentinalyze (String filename) throws FileNotFoundException {
    	int positive = 0, negative = 0;
    	Scanner scanner = new Scanner(new File(filename));
    	int longest = Math.max(posHash.longestLength(), negHash.longestLength());
    	while (scanner.hasNextLine()) {
    		String phraseToCheck = "";
    		String[] words = scanner.nextLine().split(" ");
    		int count = 0;
    		for (int i = 0; i < words.length; i++) {
    			while ((i + count) < words.length && count < longest) {
    				phraseToCheck += words[i + count];
    				count++;
    				if (hashContains(phraseToCheck, true)) {
    					positive++;
    				} else if (hashContains(phraseToCheck, false)) {
    					negative++;
    				}
    				phraseToCheck += " ";
    			}
    			phraseToCheck = "";
    			count = 0;
    		}
    	}
    	scanner.close();
    	return getSentiment(positive, negative);
    }
    private boolean hashContains(String phrase, boolean positive) {
    	if (positive) {
    		return posHash.get(phrase) != null;
    	} else {
    		return negHash.get(phrase) != null;
    	}
    }
    private String getSentiment(int positive, int negative) {
    	if (positive > negative) {
    		return "positive";
    	} else if (negative > positive) {
    		return "negative";
    	} else {
    		return "neutral";
    	}
    }
}
