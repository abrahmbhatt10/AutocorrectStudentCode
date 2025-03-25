# Autocorrect
#### A word-suggestion project created by Zach Blick for Adventures in Algorithms at Menlo School in Atherton, CA
## Your goal
Create an autocorrect tool that runs continuously in the IntelliJ terminal. Once the program begins, it waits for the user to type a word into the console. If the word matches a dictionary word, nothing happens. But if the word is misspelled, the program will return the closest matching words. If no matching candidates can be found, the program prints "No matches found."

A tester file has been provided for you. It utilizes junit tests.
To use this test file, run either the entire thing or individual tests (one at a time).
There are five test cases, each of which will load data from [test number].txt, which is in the
test_files directory.

# READ_ME Describing Project at a high level
Autocorrect has a main function that accepts user input from the terminal.  
It will look at the user's word and suggest all possible word corrections within a given threshold.
This is based on a threshold set in the code by default to 4.
It goes in a while loop and keeps asking for new words.

Autocorrect tester tests the autocorrect functions. 

# Description of the time complexity of the major methods involved
Initializing the data structures -> O(n)
Generating all matching candidates for a given misspelled word -> O(n)
Evaluating an individual candidate String to find its edit distance -> O(1)
…as well as an analysis of any other major components of your algorithm. -> O(n)
