package com.atguigu.ssyx;

//把swagger注解替换成openapi的注解，并且删除错误的导入和增加正确的导入
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * @author omf
 * @create 2023-06-08 14:41
 */
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class FileModifier {

    public static void main(String[] args) {
        String directoryPath = "."; // Current directory

        try {
            Files.walkFileTree(Paths.get(directoryPath), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
                    if (filePath.toString().endsWith(".java")) {
                        modifyJavaFile(filePath);
                        add2SecondLine(filePath);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void modifyJavaFile(Path filePath) {
        try {
            // Read the file content
            String fileContent = Files.readString(filePath);

            // Apply replacements using regular expressions
            fileContent = fileContent.replaceAll("@ApiModelProperty\\(value", "@Schema(description");
            fileContent = fileContent.replaceAll("@ApiModel\\(description", "@Schema(description");
            fileContent = fileContent.replaceAll("", "");
            fileContent = fileContent.replaceAll("", "");

            // Write modified content back to the file
            Files.writeString(filePath, fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void add2SecondLine(Path filePath) {
        try {
            // Read the file content
            List<String> fileLines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

            // Modify the content and add the import statement under the first line
            String importStatement = "import io.swagger.v3.oas.annotations.media.Schema;";
            fileLines.add(1, importStatement);

            // Write modified content back to the file with UTF-8 encoding
            Files.write(filePath, fileLines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
