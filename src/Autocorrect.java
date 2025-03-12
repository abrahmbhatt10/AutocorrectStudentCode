import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Autocorrect
 * <p>
 * A command-line tool to suggest similar words when given one not in the dictionary.
 * </p>
 * @author Zach Blick
 * @author Agastya Brahmbhatt
 */
public class Autocorrect {
private String[] dict;
private int threshold;
    /**
     * Constucts an instance of the Autocorrect class.
     * @param words The dictionary of acceptable words.
     * @param threshold The maximum number of edits a suggestion can have.
     */
    public Autocorrect(String[] words, int threshold) {
        dict = words;
        this.threshold = threshold;
    }

    public int getEditDistance(String typed, String dictWord){
        return getInsertions(typed, dictWord) + getRemovals(typed, dictWord) + getSwaps(typed, dictWord);
    }

    private int getSwaps(String typed, String dictWord) {

    }

    private int getRemovals(String typed, String dictWord) {
        int j;
        /*
            represents number of characters in typed but not in dictword.
         */
        int returnVal = 0;
        for(int i = 0; i < typed.length(); i++){
            for(j = 0; j < dictWord.length(); j++){
                if(typed.charAt(i) == dictWord.charAt(j)){
                    break;
                }
            }
            if(j == dictWord.length()){
                returnVal++;
            }
        }
        return returnVal;
    }

    private int getInsertions(String typed, String dictWord) {

    }


    /**
     * Runs a test from the tester file, AutocorrectTester.
     * @param typed The (potentially) misspelled word, provided by the user.
     * @return An array of all dictionary words with an edit distance less than or equal
     * to threshold, sorted by edit distnace, then sorted alphabetically.
     */
    public String[] runTest(String typed) {

    }


    /**
     * Loads a dictionary of words from the provided textfiles in the dictionaries directory.
     * @param dictionary The name of the textfile, [dictionary].txt, in the dictionaries directory.
     * @return An array of Strings containing all words in alphabetical order.
     */
    private static String[] loadDictionary(String dictionary)  {
        try {
            String line;
            BufferedReader dictReader = new BufferedReader(new FileReader("dictionaries/" + dictionary + ".txt"));
            line = dictReader.readLine();

            // Update instance variables with test data
            int n = Integer.parseInt(line);
            String[] words = new String[n];

            for (int i = 0; i < n; i++) {
                line = dictReader.readLine();
                words[i] = line;
            }
            return words;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}