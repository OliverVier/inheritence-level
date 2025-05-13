package de.olivervier;

import java.io.File;
import java.util.Scanner;

import de.olivervier.counter.InheritenceLevelHandler;
import de.olivervier.i18n.MessageReader;
import de.olivervier.util.FileUtil;

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
                System.out.println(MessageReader.getMessage("UI_USER_EXITS_PROGRAM"));
                break;
            }

            // Let Java File class read filepath and check for validity

            File jarFile = new File(userInput);

            if(!FileUtil.getFileExtension(jarFile).toLowerCase().equals(".jar")) {
                INST.print(MessageReader.getMessage("ERROR_IS_NOT_JAR_FILE"));
                continue;
            }

            INST.print(MessageReader.getMessage("UI_PICK_OPERATION"));
            
            int option = INST.SCANNER.nextInt();
            InheritenceLevelHandler handler = new InheritenceLevelHandler();
            
            switch (option) {
                case 1:
                    handler.countInheritenceLevel(jarFile.getAbsolutePath());
                    break;
                case 2:
                    handler.countClassesPerInheritenceLevel(jarFile.getAbsolutePath());
                    break;
                default:
                    INST.print(MessageReader.getMessage("ERROR_UNEXPECTED_INPUT"));
                    break;
            }
            break;
        }
    }

    private void print(String message) {
        System.out.println(message);
    }

    private void printHelp() {
        String helpText = MessageReader.getMessage("UI_HELP_TEXT")
                                       .formatted(ESCAPE_CHARACTERS);
        print(helpText);
    }

}
