import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Provides a factory method for creating word search games. 
 */
public class WordSearchGameFactory {

    /**
     * Returns an instance of a class that implements the WordSearchGame
     * interface.
     */
    public static WordSearchGame createGame() {
         return new Game1();
        // You must return an instance of your solution class here.
    }
}
