package de.olivervier.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.LocalDateTime;

public class ProjectLoader {
    //Compile all java into .class files
    //Create a jar file containg all class files

    private final File DESTINATION_FOLDER;

    public ProjectLoader() throws Exception {

        // Gets added in later stage.
        if(OS.getOS().equals(OS_TYPE.LINUX)) {
            throw new Exception("Not implemented yet");
        }
        
        else if(OS.getOS().equals(OS_TYPE.WINDOWS)) {
            
            boolean isDestFolderCreated = false;
            
            DESTINATION_FOLDER = new File("C:\\InheritenceLevelCounter");
            
            if(!DESTINATION_FOLDER.exists()) {
                isDestFolderCreated = DESTINATION_FOLDER.mkdir();
                if(!isDestFolderCreated) {
                    throw new Exception("Could not create destination folder");
                }
            }
        }
        
        else {
            throw new Exception("Cannot specify operating system");
        }
    }

    private String copyProject(String sourceFolder) {

        if(sourceFolder == null || sourceFolder.isEmpty()) {
            new Exception("Source folder must not be empty");
        }

        String destinationFolder = DESTINATION_FOLDER+"\\"+LocalDateTime.now().toString().replace(":", "_");
        FileUtil.copyFolder(sourceFolder, destinationFolder);

        return destinationFolder;
    }

    private void loadJAR (String pathToJar) {
        URL url;
        try {
            url = new URL("file:\\"+pathToJar);
            URLClassLoader loader = new URLClassLoader(new URL[]{url});    
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void createJAR(String pathToFolder) {
       
        File compiledFolder = new File(pathToFolder+"\\compiled");
        compiledFolder.mkdir();
        
        File file_locations = new File(compiledFolder.getAbsolutePath()+"\\file_locations.txt");

        // Compile to class files
        CommandExecutor executor = new CommandExecutor();

        String class_command1 = "cd %s".formatted(pathToFolder);
        String class_command2 = "dir /s /b *.java >> %s".formatted(file_locations.getAbsolutePath());
        String class_command3 = "javac -d %s @%s".formatted(compiledFolder.getAbsolutePath(), file_locations.getAbsolutePath());

        String concatCommands = "%s & %s & %s".formatted(class_command1, class_command2, class_command3);

        executor.runCommand(concatCommands);

        // Create JAR
        
        String jarFileName = "project.jar";
        File jarFile = new File(compiledFolder.getAbsolutePath() + "\\" + jarFileName);
        
        String jar_command1 = "cd %s".formatted(compiledFolder.getAbsolutePath());
        String jar_command2 = "jar cf %s .".formatted(jarFileName);
        
        concatCommands = "%s & %s".formatted(jar_command1, jar_command2);
        
        executor.runCommand(concatCommands);
    }

    public void loadProject(String projectClassPath) throws Exception {

        if(projectClassPath == null || projectClassPath.isEmpty()) {
            throw new Exception("projectClassPath is empty");
        }

        String currentProjectFolder = copyProject(projectClassPath);

        if(currentProjectFolder == null || currentProjectFolder.isEmpty()) {
            throw new Exception("currentProjectFolder is empty");
        }

        createJAR(currentProjectFolder);
        loadJAR(currentProjectFolder+"\\compiled\\project.jar");

    }

}
