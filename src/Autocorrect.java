import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
// I learned about how to sort arraylists alphabetically from https://stackoverflow.com/questions/5815423/sorting-arraylist-in-alphabetical-order-case-insensitive
// To do so, I had to import the collections class.
import java.util.Collections;

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
        int[][] editDistanceTable = new int[typed.length() + 1][dictWord.length() + 1];
        char[] typedChar = new char[typed.length() + 1];
        char[] dictWordChar = new char[dictWord.length() + 1];
        // I figured out how to do below line from stackoverflow.com "How to represent empty char in java character class"
        typedChar[0] = '\0';
        for(int i = 0; i < typed.length(); i++){
            typedChar[i + 1] = typed.charAt(i);
        }
        dictWordChar[0] = '\0';
        for(int i = 0; i < dictWord.length(); i++){
            dictWordChar[i + 1] = dictWord.charAt(i);
        }
        for(int i = 0; i < editDistanceTable.length; i++){
            for(int j = 0; j < editDistanceTable[0].length; j++){
                editDistanceTable[i][j] = getTabEditDistance(i, j, typedChar[i], dictWordChar[j], editDistanceTable);
            }
        }
        System.out.println(dictWord+" "+editDistanceTable[editDistanceTable.length-1][editDistanceTable[0].length-1]);
        return editDistanceTable[editDistanceTable.length - 1][editDistanceTable[0].length - 1];
    }

    public int getTabEditDistance(int i, int j, char typedChar, char dictWordChar, int[][] editDistanceTable){
        if((i == 0) && (j==0)){
            return 0;
        }
        else if (j == 0){
            return i;
        }
        else if (i == 0){
            return j;
        }
        else if(typedChar == dictWordChar){
            return editDistanceTable[i - 1][j - 1];
        }
        else{
            return 1 + Math.min(Math.min(editDistanceTable[i - 1][j], editDistanceTable[i][j - 1]), editDistanceTable[i - 1][j - 1]);
        }
    }



    /**
     * Runs a test from the tester file, AutocorrectTester.
     * @param typed The (potentially) misspelled word, provided by the user.
     * @return An array of all dictionary words with an edit distance less than or equal
     * to threshold, sorted by edit distnace, then sorted alphabetically.
     */
    public String[] runTest(String typed) {
        // I learned how to declare an array of arraylists from https://www.geeksforgeeks.org/array-of-arraylist-in-java/
        ArrayList<String>[] editDistanceArr = new ArrayList[threshold+1];
        int currentEditDistance = 0;
        // Adds dict words below threshold and edit distances to their arraylists.
        for(int i = 0; i < dict.length; i++){
            currentEditDistance = getEditDistance(typed, dict[i]);
            if(currentEditDistance <= threshold){
                if(editDistanceArr[currentEditDistance] == null) {
                    editDistanceArr[currentEditDistance] = new ArrayList<String>();
                }
                editDistanceArr[currentEditDistance].add(dict[i]);
            }
        }
        // I learned how to sort an arraylist alphabetically from https://stackoverflow.com/questions/5815423/sorting-arraylist-in-alphabetical-order-case-insensitive
        for(int i = 0; i < threshold+1; i++){
            if(editDistanceArr[i] != null) {
                Collections.sort(editDistanceArr[i]);
            }
        }
        ArrayList<String> finalReturned = new ArrayList<String>();
        for(int i = 0; i < threshold+1; i++){
            for(int j = 0; editDistanceArr[i] != null && j < editDistanceArr[i].size(); j++){
                finalReturned.add(editDistanceArr[i].get(j));
            }
        }
        System.out.println("threshold "+threshold);
        System.out.println(finalReturned);
        String[] retArr = finalReturned.toArray(new String[0]);
        //converts it to an array and return
        return retArr;
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