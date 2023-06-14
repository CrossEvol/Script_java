import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Scanner;

public class CopyAndRename {

    public static final String singleTarget = "cat";
    public static final String pluralTarget = "cats";
    public static final String singleTargetU = singleTarget.toUpperCase();
    public static final String pluralTargetU = pluralTarget.toUpperCase();
    public static final String singleTargetFU = capitalizeFirstLetter(singleTarget);
    public static final String pluralTargetFU = capitalizeFirstLetter(pluralTarget);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String keyword1, keyword2, keyword3, keyword4;

        // Section 1: Get the inputs
        System.out.print("Enter keyword1: ");
        keyword1 = scanner.nextLine().trim();

        System.out.print("Enter keyword2: ");
        keyword2 = scanner.nextLine().trim();

        keyword3 = keyword1.toUpperCase();
        keyword4 = keyword2.toUpperCase();

        // System.out.print("Enter keyword3: ");
        // keyword3 = scanner.nextLine();
        //
        // System.out.print("Enter keyword4: ");
        // keyword4 = scanner.nextLine();

        scanner.close();

        try {
            // Section 2: Create directory keyword2 and copy 'cats' recursively
            String currentDir = System.getProperty("user.dir");
            String catsDir = currentDir + "\\" + pluralTarget;
            String newDir = currentDir + "\\" + keyword2;

            createDirectory(newDir);
            copyDirectory(catsDir, newDir);

            // Section 3: Rename subdirectories and subfiles
            renameDirectoriesAndFiles(newDir, keyword1, keyword2, keyword3, keyword4);

            // Section 4: Replace content in all .java files
            replaceInJavaFiles(newDir, keyword1, keyword2, keyword3, keyword4);

            System.out.println("Program completed successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void createDirectory(String directoryPath) throws IOException {
        Files.createDirectories(Paths.get(directoryPath));
        System.out.println("Directory created: " + directoryPath);
    }

    private static void copyDirectory(String sourceDir, String targetDir) throws IOException {
        Path sourcePath = Paths.get(sourceDir);
        Path targetPath = Paths.get(targetDir);

        Files.walk(sourcePath).forEach(source -> {
            try {
                Path destination = targetPath.resolve(sourcePath.relativize(source));
                if (Files.isDirectory(source)) {
                    Files.createDirectories(destination);
                } else {
                    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                System.out.println("Error while copying directory: " + e.getMessage());
            }
        });

        System.out.println("Directory copied successfully.");
    }

    private static void renameDirectoriesAndFiles(String directoryPath, String keyword1, String keyword2,
                                                  String keyword3, String keyword4) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    renameDirectoriesAndFiles(file.getAbsolutePath(), keyword1, keyword2, keyword3, keyword4);
                    String newDirectoryName = file.getName().replaceAll("cats", keyword2);
                    newDirectoryName = newDirectoryName.replaceAll("CATS", keyword4);
                    file.renameTo(new File(file.getParent(), newDirectoryName));
                } else {
                    String newFileName = file.getName();
                    newFileName = newFileName.replaceAll(singleTarget, keyword1);
                    newFileName = newFileName.replaceAll(pluralTarget, keyword2);
                    newFileName = newFileName.replaceAll(singleTargetFU, capitalizeFirstLetter(keyword1));
                    newFileName = newFileName.replaceAll(pluralTargetFU, capitalizeFirstLetter(keyword2));
                    newFileName = newFileName.replaceAll(singleTargetU, keyword3);
                    newFileName = newFileName.replaceAll(pluralTargetU, keyword4);
                    file.renameTo(new File(file.getParent(), newFileName));
                }
            }
        }
    }

    private static void replaceInJavaFiles(String directoryPath, String keyword1, String keyword2,
                                           String keyword3, String keyword4) throws IOException {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    replaceInJavaFiles(file.getAbsolutePath(), keyword1, keyword2, keyword3, keyword4);
                } else if (file.getName().endsWith(".ts")) {
                    replaceKeywordsInFile(file, keyword1, keyword2, keyword3, keyword4);
                }
            }
        }
    }

    private static void replaceKeywordsInFile(File file, String keyword1, String keyword2, String keyword3,
                                              String keyword4) throws IOException {

        try {
            // Read the file content
            String fileContent = Files.readString(Path.of(file.getAbsolutePath()));

            // Apply replacements using regular expressions
            fileContent = fileContent.replaceAll(singleTarget, keyword1);
            fileContent = fileContent.replaceAll(pluralTarget, keyword2);
            fileContent = fileContent.replaceAll(singleTargetFU, capitalizeFirstLetter(keyword1));
            fileContent = fileContent.replaceAll(pluralTargetFU, capitalizeFirstLetter(keyword2));
            fileContent = fileContent.replaceAll(singleTargetU, keyword3);
            fileContent = fileContent.replaceAll(pluralTargetU, keyword4);

            // Write modified content back to the file
            Files.writeString(Path.of(file.getAbsolutePath()), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // BufferedReader reader = new BufferedReader(new FileReader(file));
        // StringBuilder content = new StringBuilder();
        // String line;
        //
        // while ((line = reader.readLine()) != null) {
        //     line = line.replaceAll("cat", keyword1);
        //     line = line.replaceAll("cats", keyword2);
        //     line = line.replaceAll("Cat", capitalizeFirstLetter(keyword1));
        //     line = line.replaceAll("Cats", capitalizeFirstLetter(keyword2));
        //     line = line.replaceAll("CAT", keyword3);
        //     line = line.replaceAll("CATS", keyword4);
        //     content.append(line).append("\n");
        // }
        //
        // reader.close();
        //
        // BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        // writer.write(content.toString());
        // writer.close();
    }

    public static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }


}
