package homework;

public class ListFiles {
    public static String[] listAllFiles(String dirPath) {
        // String decodedPath = URLDecoder.decode(dirPath, "UTF-8");
        // System.err.println(dirPath);

        // String succPath = "/D:/Codes_and_Projects/PlantsVsZombines/pvz/target/classes/images/Zombies/NormalZombie/Zombie";
        java.io.File directory = new java.io.File(dirPath);
        // System.err.println(succPath);

        // System.err.println("List: ");
        // for(String d : directory.list()) System.err.println(d);
        // System.out.println(directory.getAbsolutePath());
        // System.exit(0);
        java.io.File[] files = directory.listFiles();

        // System.err.println("dirPath = " + dirPath);
        // System.err.println("files = " + files);
        String[] result = new String[files.length];
        for(int i = 0; i < result.length; i++)
            result[i] = "file:/" + files[i].getAbsolutePath();

        return result;
    }
}