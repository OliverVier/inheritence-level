package de.olivervier.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandExecutor {

    private ProcessBuilder shell;
    private final List<String> WINDOWS_SHELL = new ArrayList<String>(Arrays.asList("cmd.exe","/c"));
    private final List<String> LINUX_SHELL = null;

    public CommandExecutor() {
        if(shell == null) {
            shell = getOSShell();
        }
    }
    
    public void runCommand(String command) {

        shell.command().clear();
        
        ArrayList<String> newCommands = new ArrayList<>();
        newCommands.addAll(WINDOWS_SHELL);
        newCommands.add(command);
        
        shell.command(newCommands);

        try {
            Process p = shell.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ProcessBuilder getOSShell() {
        
        if(OS.getOS().equals(OS_TYPE.LINUX)) {
            System.err.println("Tried running from Linux based OS");
            return null;
        }

        else if(OS.getOS().equals(OS_TYPE.WINDOWS)) {
            
            return new ProcessBuilder(new ArrayList<>(WINDOWS_SHELL));
        }

        else {
            System.err.println("Tried running from unspecified OS");
            return null;
        }
    } 
}