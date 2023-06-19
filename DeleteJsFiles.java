import java.io.File;
import java.io.IOException;

//清除莫名产生的js文件
public class DeleteJsFiles {

    public static void main(String[] args) throws IOException {
        File currentDir = new File(".");
        File[] files = currentDir.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".js")) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteJsFiles(file);
            }
        }
    }

    private static void deleteJsFiles(File directory) throws IOException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".js")) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteJsFiles(file);
            }
        }
    }
}
