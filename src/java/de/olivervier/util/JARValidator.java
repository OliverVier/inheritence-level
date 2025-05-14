package de.olivervier.util;

import java.io.File;

import de.olivervier.i18n.MessageReader;

public class JARValidator {
    
    public static boolean isJARFileValid(File file) {
        
        if(file == null || !file.exists()) {
            Logger.printInfo(MessageReader.getMessage("ERROR_FILE_DOES_NOT_EXIST"));
            return false;
        }

        if(FileUtil.getFileExtension(file) == null) {
            Logger.printInfo(MessageReader.getMessage("ERROR_FILE_NO_JAR_EXTENSION"));
            return false;
        }

        if(!FileUtil.getFileExtension(file).toLowerCase().equals(".jar")) {
            Logger.printInfo(MessageReader.getMessage("ERROR_IS_NOT_JAR_FILE"));
            return false;
        }
        
        return true;
    }
}