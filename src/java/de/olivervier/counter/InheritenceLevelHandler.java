package de.olivervier.counter;

import java.util.Map;

public class InheritenceLevelHandler {
     
    public void countInheritenceLevel(String basePath) {
        InheritenceLevelCounter counter = new InheritenceLevelCounter();
        Map<String, Integer> inheritenceLevelPerName = counter.countInheritenceLevel(basePath);
        printInheritanceLevelPerFile(inheritenceLevelPerName);
    }

    private void printInheritanceLevelPerFile(Map<String, Integer> inheritanceLevelPerFile) {
        inheritanceLevelPerFile.entrySet()
                                .stream()
                                .sorted((e1,e2) -> Integer.compare(e1.getValue(), e2.getValue()))
                                .forEach(e -> System.out.println("L%d - %s".formatted(e.getValue(), e.getKey())));
    }

}
