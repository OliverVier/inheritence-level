package de.olivervier.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FileUtil {

    /**
     * Reads filepath independent from
     * OS folder separator ('/' or '\')
     * @return absolute file path
     */
    public static String getFilePath(String absolutePath) {
        return new File(absolutePath).getAbsolutePath();
    }

    /**
     * Read file extension of absolutePath
     * 
     * Examples
     * 
     * <p>- test.pdf -> pdf</p>
     * <p>- test.p -> p </p>
     * <p>- test. -> null </p>
     * <p>- test -> null </p>
     * 
     * @param absolutePath
     * @return file-extension when exists, returns null when extension length is 
     * 0 or file has no extension
     */
    public static String getFileExtension(String absolutePath) {
        return readFileNameExtension(absolutePath);
    }

    /**
     * Read file extension of file
     * 
     * Examples
     * 
     * <p>- test.pdf -> pdf</p>
     * <p>- test.p -> p </p>
     * <p>- test. -> null </p>
     * <p>- test -> null </p>
     * 
     * @param file
     * @return file-extension when exists, returns null when extension length is 
     * 0 or file has no extension
     */
    public static String getFileExtension(File file) {
        return readFileNameExtension(file.getName());
    }

    private static String readFileNameExtension(String name) {
        int lastDotIndex = name.lastIndexOf('.');
        if(lastDotIndex == -1) {
            return null;
        }

        String extension = name.substring(lastDotIndex, name.length());
        return extension.length() > 0 ? extension : null;
    }
    
    //TODO: Add check, whether paths actually go together
    /**
     * Finds the relative path between a base and <b>known</b> subfolder-path.
     * </br>
     * <br> - basepath:      C:\\Homework\ </br>
     * <br> - subfolder:     C:\\Homework\Math\homework1.pdf </br>
     * <br> - relative path: Math\homework1.pdf </br>
     *
     * @return relative path
     */
    public static String getRelativePath(String basepath, String subfolderPath) {

        if(basepath.equals(subfolderPath)) {
            return "";
        }

        String relativePathName = subfolderPath.replace(basepath, "");
		if(relativePathName.charAt(0) == File.separatorChar) {
			relativePathName = relativePathName.substring(1, relativePathName.length());
		}
        return relativePathName;
    }

    /**
     * Converts a relative file path for example in a project to java package notation. 
     * For example, if you have a project called summer as a project-path and a subfolder 
     * util containing a example.java the method will convert util\example.java to util.example 
     * -
     * -
     * @param relativePath "util/example.java" or "util\example.java"
     * @return
     */
    public static String convertFolderToPackageName(String relativePath) {

        String packageName = relativePath;

        if(packageName == null) {
            return null;
        } 
        if(packageName.isBlank()) {
            return "";
        }

        //Remove first character if '/' or '\'
        if(packageName.charAt(0) == '\\' || packageName.charAt(0) == '/') {
            packageName.substring(1, packageName.length());
        }

        //Convert other occurences of '/' or '\' to dots
        packageName = packageName.replace("\\", ".");
        packageName = packageName.replace("/", ".");

        //Remove extension in filename
        int lastDotIndex = 0;                
        if((lastDotIndex = packageName.lastIndexOf('.')) == -1) {
            lastDotIndex = packageName.length();
        }

        return packageName.substring(0, lastDotIndex);
    }

    public static void main(String[] args) {
        FileUtil fileUtil = new FileUtil();
        fileUtil.copyFolder("C:\\Users\\ovier\\git\\inheritence_level_counter\\src",
                            "C:\\Users\\ovier\\git\\inheritence_level_counter\\projects\\"+LocalDateTime.now().toString().replace(":", "_"));
    }

    /**
     * 
     * @param sourcePath
     * @param destinationPath
     */
    public void copyFolder(String sourceURI, String destinationURI) {
        
        File sourceDirectory = new File(sourceURI);
        
        if(!sourceDirectory.exists() || !sourceDirectory.isDirectory()) {
            String errorMsg = """
                    Source-Pfad: %s
                    Existiert? %b
                    Ist Ordner? %b
                    """.formatted(sourceDirectory.getAbsolutePath(),
                                  sourceDirectory.exists(),
                                  sourceDirectory.isDirectory());
            System.err.println(errorMsg);
            return;
        }

        Path sourcePath = Paths.get(sourceURI);
        try { 
            Files.walk(sourcePath).forEach(sourceSubPath -> {
                Path destinationSubPath = Paths.get(destinationURI, 
                                                    FileUtil.getRelativePath(sourceURI, sourceSubPath.toString()));
                try {
					Files.copy(sourceSubPath, destinationSubPath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}