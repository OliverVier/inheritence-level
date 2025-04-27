package de.olivervier.counter;

import java.util.Map;

public class InheritenceLevelHandler {
     
    public void countInheritenceLevel(String basePath) {
        InheritenceLevelCounter counter = new InheritenceLevelCounter();
        Map<String, Integer> inheritenceLevelPerName = counter.countInheritenceLevel(basePath);
        printInheritanceLevelPerFile(inheritenceLevelPerName);
    }

    private void printInheritanceLevelPerFile(Map<String, Integer> inheritanceLevelPerFile) {
        inheritanceLevelPerFile.forEach((name, value) -> System.out.println(name + ": " + value));
    }

}
