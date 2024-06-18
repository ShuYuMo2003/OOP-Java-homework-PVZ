package homework;

import java.util.Comparator;

public class ListFiles {
    public static String[] listAllFiles(String dirPath) {


        // String succPath = "/D:/Codes_and_Projects/PlantsVsZombines/pvz/target/classes/images/Zombies/NormalZombie/Zombie";
        java.io.File directory = new java.io.File(dirPath);

        java.io.File[] files = directory.listFiles();

        // System.err.println("dirPath = " + dirPath);
        // System.err.println("files = " + files);
        String[] result = new String[files.length];
        for(int i = 0; i < result.length; i++)
            result[i] = "file:/" + files[i].getAbsolutePath();

        java.util.Arrays.sort(result, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                String aid = a.substring(a.lastIndexOf('_') + 1, a.lastIndexOf('.'));
                String bid = b.substring(b.lastIndexOf('_') + 1, b.lastIndexOf('.'));
                try {
                    return Integer.compare(Integer.parseInt(aid), Integer.parseInt(bid));
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        });

        return result;
    }
}