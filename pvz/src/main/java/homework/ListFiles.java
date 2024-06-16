package homework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ListFiles {
    public static String[] listAllFiles(String dirPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(dirPath))) {
            return paths.filter(path -> Files.isRegularFile(path))
                        .map(path -> path.toString())
                        .toArray(size -> new String[size]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}