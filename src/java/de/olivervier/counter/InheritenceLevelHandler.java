package de.olivervier.counter;

import java.util.Map;

import de.olivervier.util.Logger;

public class InheritenceLevelHandler {
     
    public void countInheritenceLevel(String basePath) {
        InheritenceLevelCounter counter = new InheritenceLevelCounter();
        Map<String, Integer> inheritenceLevelPerName = counter.countLevelPerClass(basePath);
        printLevelPerClass(inheritenceLevelPerName);
    }

    public void countClassesPerLevel(String basePath) {
        InheritenceLevelCounter counter = new InheritenceLevelCounter();
        Map<Integer, Integer> amountOfClassesPerLevel = counter.countClassesPerLevel(basePath);
        printAmountOfClassesPerLevel(amountOfClassesPerLevel);
    }

    private void printLevelPerClass(Map<String, Integer> inheritanceLevelPerFile) {
        inheritanceLevelPerFile.entrySet()
                                .stream()
                                .sorted((e1,e2) -> Integer.compare(e1.getValue(), e2.getValue()))
                                .forEach(e -> Logger.printInfo("L%d - %s".formatted(e.getValue(), e.getKey())));
    }

    private void printAmountOfClassesPerLevel(Map<Integer, Integer> amountOfClassesPerLevel) {
        amountOfClassesPerLevel.entrySet()
                                .stream()
                                .sorted((e1,e2) -> Integer.compare(e1.getKey(), e2.getKey()))
                                .forEach(e -> Logger.printInfo("L%d - %d".formatted(e.getKey(), e.getValue())));
    }

}
