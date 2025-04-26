package de.olivervier;

import java.io.File;
import java.util.Scanner;

import de.olivervier.counter.InheritenceLevelHandler;
import de.olivervier.util.ProjectLoader;

public class Main {
    
    private static final Main INST = new Main();
    private final String ESCAPE_CHARACTERS = "q";
    private final String ERROR_IS_NOT_DIRECTORY = "Given filepath is not pointing at a directory.";
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

            File file = new File(userInput);

            if(!file.isDirectory()) {
                INST.print(INST.ERROR_IS_NOT_DIRECTORY);
                continue;
            }

            // Before going through each class, we have to make sure to load the JAR,
            // so that the classes are known.

            try {
                new ProjectLoader().loadProject(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new InheritenceLevelHandler().countInheritenceLevel(file.getAbsolutePath());
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
        Type java classpath to project
        for example: C:\\users\\...\\git\\Project\\src\\java
        """.formatted(ESCAPE_CHARACTERS);

        print(helpText);
    }

}
