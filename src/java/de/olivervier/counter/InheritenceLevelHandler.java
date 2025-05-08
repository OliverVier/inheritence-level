package de.olivervier.counter;

import java.util.Map;

public class InheritenceLevelHandler {
     
    public void countInheritenceLevel(String basePath) {
        InheritenceLevelCounter counter = new InheritenceLevelCounter();
        Map<String, Integer> inheritenceLevelPerName = counter.countInheritenceLevel(basePath);
        printInheritanceLevelPerFile(inheritenceLevelPerName);
    }

    public void countClassesPerInheritenceLevel(String basePath) {
        InheritenceLevelCounter counter = new InheritenceLevelCounter();
        Map<Integer, Integer> amountOfClassesPerLevel = counter.groupByInheritenceLevel(basePath);
        printClassesPerInheritanceLevelPerFile(amountOfClassesPerLevel);
    }

    private void printInheritanceLevelPerFile(Map<String, Integer> inheritanceLevelPerFile) {
        inheritanceLevelPerFile.entrySet()
                                .stream()
                                .sorted((e1,e2) -> Integer.compare(e1.getValue(), e2.getValue()))
                                .forEach(e -> System.out.println("L%d - %s".formatted(e.getValue(), e.getKey())));
    }

    private void printClassesPerInheritanceLevelPerFile(Map<Integer, Integer> amountOfClassesPerLevel) {
        amountOfClassesPerLevel.entrySet()
                                .stream()
                                .sorted((e1,e2) -> Integer.compare(e1.getKey(), e2.getKey()))
                                .forEach(e -> System.out.println("L%d - %d".formatted(e.getKey(), e.getValue())));
    }

}
