import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
        char[][] editDistanceTable = new char[typed.length() + 1][dictWord.length() + 1];
        editDistanceTable[1][0] = ' ';
        editDistanceTable[0][1] = ' ';
        for(int i = 1; i < typed.length(); i++){
            editDistanceTable[i + 1][0] = typed.charAt(i);
        }
        for(int j = 1; j < dictWord.length(); j++){
            editDistanceTable[0][j + 1] = dictWord.charAt(j);
        }
        for(int i = 1; i < typed.length() + 1; i++){
            for(int j = 1; j < dictWord.length(); j++){
                editDistanceTable[i][j] = getRecurseEditDistance(i, j, typed, dictWord, editDistanceTable);
            }
        }
        return editDistanceTable[typed.length() + 1][dictWord.length() + 1];
    }

    public char getRecurseEditDistance(int i, int j, String typed, String dictWord, char[][] editDistanceTable){
        if(typed.charAt(i) == typed.charAt(j)){
            return editDistanceTable[i - 1][j - 1];
        }
        return (char) Math.min(Math.min(editDistanceTable[i - 1][j], editDistanceTable[i][j - 1]), editDistanceTable[i - 1][j - 1]);
    }



    /**
     * Runs a test from the tester file, AutocorrectTester.
     * @param typed The (potentially) misspelled word, provided by the user.
     * @return An array of all dictionary words with an edit distance less than or equal
     * to threshold, sorted by edit distnace, then sorted alphabetically.
     */
    public String[] runTest(String typed) {
        // I learned how to declare an array of arraylists from https://www.geeksforgeeks.org/array-of-arraylist-in-java/
        ArrayList<String>[] editDistanceArr = new ArrayList[threshold];
        int currentEditDistance = 0;
        String[] finalReturnedArr = new String[0];
        // Adds dict words below threshold and edit distances to their arraylists.
        for(int i = 0; i < dict.length; i++){
            currentEditDistance = getEditDistance(typed, dict[i]);
            if(currentEditDistance <= threshold){
                editDistanceArr[currentEditDistance].add(dict[i]);
            }
        }
        //Sort Arraylist by edit distance.
        //Sort arraylist alphabetically.
        //convert it to an array and return
        return finalReturnedArr;
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