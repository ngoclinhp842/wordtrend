import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/* 
File: WordTrendsFinder.java
Author: Linh Phan (Michelle)
Date: 04/24/2022

WordTrendsFinder class that determines the frequency of a specific set of words 
across many different word files.

USAGE java WordTrendsFinder <BaseFilename> <FileNumberBegin> <FileNumberEnd> <Word1> <Word2> ...
 where <BaseFilename> contains the text part of the name of each WordCount file to analyze.
 and <FileNumberBegin> specifies the first file's number suffix
 and <FileNumberEnd> specifies the last number suffix in the range of word files to analyze.
 <Word1> <Word2> ... is the list of words to analyze.

how to run: type "javac WordTrendsFinder.java"
            type "java WordTrendsFinder wordCounter_ 2008 2015 sony portal ipad syntax facebook friend"

 */
public class WordTrendsFinder {
    
    public static void main( String[] argv ) throws FileNotFoundException, IOException{
        WordCounter2 counter = new WordCounter2("hashmap");

        // take data from the command line
        String filenameBase = argv[0];
        int fileNumBegin = Integer.parseInt(argv[1]);
        int fileNumEnd = Integer.parseInt(argv[2]);

        // create an array of words in the command line
        ArrayList<String> words = new ArrayList<>();
        for (int i = 3; i < argv.length; i++){
            words.add(argv[i]);
        }

        // store the number of words in the command line
        int words_length = words.size();
        // create a 2D array. The column is year from fileNumBegin to fileNumEnd
        // Each row is the frequency of each word in the list of words in the command line
        Double[][] table = new Double[fileNumEnd-fileNumBegin + 1][words_length];

        // store the first line, column ID
        String columnID = " , ";
        String filename;
        Double frequency;
        
        for (int i = fileNumBegin; i <= fileNumEnd; i++){
            // update the columnID
            columnID += String.valueOf(i) + ", ";
            // construct the filename
            filename = filenameBase + String.valueOf(i) + ".txt";
            
            // use HashMap generated by readWordCount method in your WordCounter class
            counter.readWordCount(filename);
            // System.out.println(counter);

            // find the frequency for each words in the command line
            for (int j = 0; j < words_length; j++){
                frequency = counter.getCount(words.get(j)) * 1.0/counter.totalWordCount();
                // store the frequency in the 2D array's corresponding position 
                table[i - fileNumBegin][j] = frequency;
            }
        }

        // print out the columnID first
        System.out.println(columnID);

        // for each words in the command line
        for (int i = 0; i < words_length; i++){
            // contrust each row
            String row = "";
            // add the row ID
            row += words.get(i) + ", ";

            // loop m times (number of filename)
            for (int j = fileNumBegin; j <= fileNumEnd; j++){
                // add the row data points
                row += table[j - fileNumBegin][i] + ", ";
            }
            // print out the row
            System.out.println(row);
        }
    }
}
