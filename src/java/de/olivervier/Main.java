package de.olivervier;

import java.io.File;
import java.util.Scanner;

import de.olivervier.counter.InheritenceLevelHandler;
import de.olivervier.i18n.MessageReader;
import de.olivervier.util.JARValidator;
import de.olivervier.util.Logger;

public class Main {
    
    private static final Main INST = new Main();
    private final Scanner SCANNER = new Scanner(System.in);
    private final String ESCAPE_CHARACTERS = "q";

    public static void main(String[] args) {

        while(true) {

            INST.printHelp();
            
            String userInput = INST.SCANNER.next();
            
            if(userInput.isEmpty()) {
                continue;
            }

            if(userInput.equals(INST.ESCAPE_CHARACTERS)) {
                Logger.printInfo(MessageReader.getMessage("UI_USER_EXITS_PROGRAM"));
                break;
            }

            // Let Java File class read filepath and check for validity

            File jarFile = new File(userInput);
            
            if(!JARValidator.isJARFileValid(jarFile)) {
                continue;
            }

            Logger.printInfo(MessageReader.getMessage("UI_PICK_OPERATION"));
            
            int option = INST.SCANNER.nextInt();
            InheritenceLevelHandler handler = new InheritenceLevelHandler();
            
            switch (option) {
                case 1:
                    handler.countInheritenceLevel(jarFile.getAbsolutePath());
                    break;
                case 2:
                    handler.countClassesPerLevel(jarFile.getAbsolutePath());
                    break;
                default:
                    Logger.printInfo(MessageReader.getMessage("ERROR_UNEXPECTED_INPUT"));
                    break;
            }
            break;
        }
    }

    private void printHelp() {
        String helpText = MessageReader.getMessage("UI_HELP_TEXT")
                                       .formatted(ESCAPE_CHARACTERS);
        Logger.printInfo(helpText);
    }

}
