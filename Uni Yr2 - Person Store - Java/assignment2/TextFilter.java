package assignment2;

import java.io.*;

/**
 * This class constructs a 'text filter' given by another class to easily set up a file type to filter.
 * For example when a user opens a dialogue to open or save a file.
 * The particular string given by another class corrisponds to the filename(s) that will only
 * be displayed by the save or open dialogue (e.g. GUI class)
 * 
 * @author Jamie Brindle ID: 06352322
 *
 */
public class TextFilter extends javax.swing.filechooser.FileFilter {

    String fileType;

    public TextFilter(String s) {
        fileType = s;
    }

    /**
     * Display only directories and the fileType given by another class or method 
     * @return TRUE of file ends with fileType (string given by another class) and is a directory
     */
    public boolean accept(File file) {
        return file.getName().toLowerCase().endsWith(fileType) || file.isDirectory();
    }

    /**
     * @return description of fileType the TextFilter class is currently set to
     */
    public String getDescription() {
        return "Files of file type:  " + fileType;
    }
}