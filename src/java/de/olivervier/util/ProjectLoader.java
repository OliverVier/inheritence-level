package de.olivervier.util;

import java.io.File;
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

    public void copyProject(String sourceFolder) {

        if(sourceFolder == null || sourceFolder.isEmpty()) {
            new Exception("Source folder must not be empty");
        }

        FileUtil.copyFolder(sourceFolder, DESTINATION_FOLDER+"\\"+LocalDateTime.now().toString().replace(":", "_"));
    }

    public void loadJAR () {

    }

}
