import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.Set;
import java.util.Scanner;
import java.util.TreeSet;

import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Iterator;
import java.lang.Math; 


public class Game1 implements WordSearchGame {
    // field of attributes for class
    // 2D array
    private String[][] board;

    // position of letter in board
    private boolean[][] position;

    // N --> size of side (of square board)
    private int N;

    // state to show whether we load Lexicon/not
    private boolean lexiconState;

    // TreeSet - class to implement Set interface
    TreeSet<String> lexicon;

    // Constructor of game
    public Game1 () {
        String[] a = {"E", "E", "C", "A", "A", "L", "E", "P", "H", "N", "B", "O", "Q", "T", "T", "Y"};
        setBoard(a);
        N = 4;
        lexiconState = false;
        position = new boolean[N][N];
        // intial positions - empty; 
        // i = row #, j = column #
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                position[i][j] = false;
            }
        }
    }
    /**
     * Loads the lexicon into a data structure for later use. 
     * 
     * @param fileName A string containing the name of the file to be opened.
     * @throws IllegalArgumentException if fileName is null
     * @throws IllegalArgumentException if fileName cannot be opened.
     */
    public void loadLexicon(String fileName) {
        String str = "";
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        lexicon = new TreeSet<String>();
        try {
            Scanner s = new Scanner(new BufferedReader(new FileReader(new File(fileName))));
            while (s.hasNext()) {
                str = s.next();
                boolean added = lexicon.add(str.toUpperCase());
                if (s.hasNext()) {
                    s.nextLine();
                }
                lexiconState = true;
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Error loading word list: " + fileName + ": " + e + " " + str);
        }
    }

    /**
     * Stores the incoming array of Strings in a data structure that will make
     * it convenient to find words.
     * 
     * @param letterArray This array of length N^2 stores the contents of the
     *     game board in row-major order. Thus, index 0 stores the contents of board
     *     position (0,0) and index length-1 stores the contents of board position
     *     (N-1,N-1). Note that the board must be square and that the strings inside
     *     may be longer than one character.
     * @throws IllegalArgumentException if letterArray is null, or is  not
     *     square.
     */
    public void setBoard(String[] letterArray) {
        //stores incoming array of box of letters
        // instantiate letters (0,0) -> (N-1,N-1)
        // double while loop? - set
        if (letterArray == null) {
            throw new IllegalArgumentException("Null Letter Array");
        }
        // make board into square 
        int n = (int)Math.sqrt(letterArray.length);
        if (letterArray.length - (n*n) != 0) {
            throw new IllegalArgumentException("Array is not square");
        }
        int i = 0;
        int j = 0;
        N = n;
        int c = 0;
        board = new String[N][N];
        position = new boolean[N][N];

        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++) {
                // c - index of letter from letterArray
                board[i][j] = letterArray[c];
                c++;
                // set up each position as empty
                position[i][j] = false;
            }
        }
    }

    /**
     * Creates a String representation of the board, suitable for printing to
     *   standard out. Note that this method can always be called since
     *   implementing classes should have a default board.
     */
    public String getBoard() {
        // set output as String, store all letters in String
        int i;
        int j;
        String boardLetters = "";
        for (i = 0; i < board[i].length; i++) {
            for (j = 0; j < board[i].length; j++) {
                boardLetters += " " + board[i][j] + " ";
            } 
            boardLetters += "\n";
        }
        return boardLetters;
    }
    /** 
     * Retrieves all scorable words on the game board, according to the stated game
     * rules.
     * 
     * @param minimumWordLength The minimum allowed length (i.e., number of
     *     characters) for any word found on the board.
     * @return java.util.SortedSet which contains all the words of minimum length
     *     found on the game board and in the lexicon.
     * @throws IllegalArgumentException if minimumWordLength < 1
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    public SortedSet<String> getAllScorableWords(int minWordLength) {
        if (minWordLength < 1) {
            throw new IllegalArgumentException();
        } if (lexiconState == false) {
            throw new IllegalStateException("Lexicon not loaded");
        }

        SortedSet<String> n = new TreeSet<String>();
        
        int i = 0;
        int j = 0;

        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++) {
                
            }
        }
        
        return n;
    }

    /**
    * Computes the cummulative score for the scorable words in the given set.
    * To be scorable, a word must (1) have at least the minimum number of characters,
    * (2) be in the lexicon, and (3) be on the board. Each scorable word is
    * awarded one point for the minimum number of characters, and one point for 
    * each character beyond the minimum number.
    *
    * @param words The set of words that are to be scored.
    * @param minimumWordLength The minimum number of characters required per word
    * @return the cummulative score of all scorable words in the set
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */  
    public int getScoreForWords(SortedSet<String> words, int minWordLength) {
        if (minWordLength < 1) {
            throw new IllegalArgumentException();
        } if (lexiconState == false) {
            throw new IllegalStateException("Lexicon not loaded");
        }
        int total = 0;

        for (String word : words) {
            if (isValidWord(word)) {
                if (isOnBoard(word).isEmpty()) {
                    if (word.length() >= minWordLength) {
                        if (word.length() == minWordLength) {    
                            total += 1;
                        } else {
                            total += word.length() - minWordLength;
                        }
                    }
                }
            }
        }
        return total;
    }

    /**
     * Determines if the given word is in the lexicon.
     * 
     * @param wordToCheck The word to validate
     * @return true if wordToCheck appears in lexicon, false otherwise.
     * @throws IllegalArgumentException if wordToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    public boolean isValidWord(String wordToCheck) {
        if (wordToCheck == null) {
            throw new IllegalArgumentException();
        } if (lexiconState == false) {
            throw new IllegalStateException("Lexicon not loaded");
        }

        // maybe need upper/lower case check?
        return lexicon.contains(wordToCheck);
    }

    /**
     * Determines if there is at least one word in the lexicon with the 
     * given prefix.
     * 
     * @param prefixToCheck The prefix to validate
     * @return true if prefixToCheck appears in lexicon, false otherwise.
     * @throws IllegalArgumentException if prefixToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    public boolean isValidPrefix(String prefixToCheck) {
        if (prefixToCheck == null) {
            throw new IllegalArgumentException();
        } if (lexiconState == false) {
            throw new IllegalStateException("Lexicon not loaded");
        }
        // maybe need upper/lower case check?
        boolean found = false;
        int len = prefixToCheck.length();
        for (String word : lexicon) {
            word = word.substring(0, len-1);
            if (word.equals(prefixToCheck)) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Determines if the given word is in on the game board. If so, it returns
     * the path that makes up the word.
     * @param wordToCheck The word to validate
     * @return java.util.List containing java.lang.Integer objects with  the path
     *     that makes up the word on the game board. If word is not on the game
     *     board, return an empty list. Positions on the board are numbered from zero
     *     top to bottom, left to right (i.e., in row-major order). Thus, on an NxN
     *     board, the upper left position is numbered 0 and the lower right position
     *     is numbered N^2 - 1.
     * @throws IllegalArgumentException if wordToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    public List<Integer> isOnBoard(String wordToCheck) {
        if (wordToCheck == null) {
            throw new IllegalArgumentException();
        } if (lexiconState == false) {
            throw new IllegalStateException("Lexicon not loaded");
        }
        // depth first search w/ breadth first search inside of boolean[][]?
        List<Integer> n = new ArrayList<Integer>();

        return n;
    }
/*public boolean dfs(int x, int y) {
        // use board
        found = false;
        while ((n != null) && (!found)) {
            if (n.element == target) {
            found = true ;
            } else if (n.element > target) {
                n = n.left;
            } else {
                n = n.right;
            }
            return found;
        }
*/
}
