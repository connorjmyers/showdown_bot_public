package Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TextUtility {

    /**
     * Reads a .txt file and returns an ArrayList representation of the data
     *
     * @param directory The location of the .txt file in String format, e.g. "\\Database\\Logins\\example.txt"
     * @return Data inside file, returned in ArrayList<String> format
     * @throws IOException If there is no file at directory, this exception will be thrown.
     */
    public static ArrayList<String> readFile(String directory) throws IOException {
        ArrayList<String> fileContents = new ArrayList<>();
        File textFile = new File(directory);
        BufferedReader br = new BufferedReader(new FileReader(textFile));

        String data;
        while ((data = br.readLine()) != null) {
            fileContents.add(data);
        }
        return fileContents;
    }

    /**
     * Returns ArrayList format of .txt file which is of format data1,data2,data3...
     *
     * @param directory The path for the file
     * @return ArrayList of data in file.
     */
    public static ArrayList<String> parseFile(String directory) {

        ArrayList<String> toParse = null;
        try {
            toParse = readFile(directory);
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return new ArrayList<>(Arrays.asList(toParse.get(0).split(",")));
    }
}
