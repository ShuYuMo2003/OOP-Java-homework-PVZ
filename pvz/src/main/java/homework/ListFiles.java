package homework;

import java.util.Comparator;

/**
 * Utility class to list all files in a directory and sort them based on a specific naming convention.
 */
public class ListFiles {

    /**
     * Lists all files in the specified directory path and sorts them based on a custom naming convention.
     * @param dirPath The directory path from which files are to be listed.
     * @return An array of strings representing the absolute file paths sorted by a numeric identifier in the filename.
     */
    public static String[] listAllFiles(String dirPath) {
        // Create a File object for the specified directory path
        java.io.File directory = new java.io.File(dirPath);

        // List all files in the directory
        java.io.File[] files = directory.listFiles();

        // Initialize a string array to store the resulting file paths
        String[] result = new String[files.length];

        // Iterate through each file and construct its absolute path
        for (int i = 0; i < result.length; i++) {
            result[i] = "file:" + files[i].getAbsolutePath();
        }

        // Sort the array of file paths based on a custom comparator
        java.util.Arrays.sort(result, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                // Extract numeric identifiers from filenames
                String aid = a.substring(a.lastIndexOf('_') + 1, a.lastIndexOf('.'));
                String bid = b.substring(b.lastIndexOf('_') + 1, b.lastIndexOf('.'));
                try {
                    // Compare numeric identifiers as integers
                    return Integer.compare(Integer.parseInt(aid), Integer.parseInt(bid));
                } catch (NumberFormatException e) {
                    // Handle non-numeric cases by returning 0 (no preference in order)
                    return 0;
                }
            }
        });

        return result;  // Return the sorted array of file paths
    }
}
