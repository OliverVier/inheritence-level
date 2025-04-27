package de.olivervier;

import java.io.File;
import java.util.Scanner;

import de.olivervier.counter.InheritenceLevelHandler;
import de.olivervier.util.FileUtil;

public class Main {
    
    private static final Main INST = new Main();
    private final String ESCAPE_CHARACTERS = "q";
    private final String ERROR_IS_NOT_JAR_FILE = "Given filepath is not pointing at a JAR file.";
    private final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        while(true) {

            INST.printHelp();
            
            String userInput = INST.SCANNER.next();
            
            if(userInput.isEmpty()) {
                continue;
            }

            if(userInput.equals(INST.ESCAPE_CHARACTERS)) {
                System.out.println("Quitting program");
                break;
            }

            // Let Java File class read filepath and check for validity

            File jarFile = new File(userInput);

            if(!FileUtil.getFileExtension(jarFile).toLowerCase().equals(".jar")) {
                INST.print(INST.ERROR_IS_NOT_JAR_FILE);
                continue;
            }
  
            InheritenceLevelHandler handler = new InheritenceLevelHandler();
            handler.countInheritenceLevel(jarFile.getAbsolutePath());
            
            break;
        }
    }

    private void print(String message) {
        System.out.println(message);
    }

    private void printHelp() {

        String helpText = 
        """
        ____________________________________________________

        Type '%s' to exit
        or
        Type path to JAR file
        for example: C:\\users\\...\\Desktop\\project.jar
        """.formatted(ESCAPE_CHARACTERS);

        print(helpText);
    }

}
