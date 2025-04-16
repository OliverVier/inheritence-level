package de.olivervier.counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.olivervier.reader.ClassFinder;

public class InheritenceLevelCounter {

    public void execute(String basePath) {

        if(basePath == null) {
            System.err.println("Cannot get execution path");
            return;
        }

        ClassFinder classFinder = new ClassFinder();
        List<Class> classes = classFinder.findClassesAtURI(basePath);
        Map<String, Integer> inheritanceLevelPerFile = findSumOfInheritanceLevel(classes);

        printInheritanceLevelPerFile(inheritanceLevelPerFile);
    }

    private void printInheritanceLevelPerFile(Map<String, Integer> inheritanceLevelPerFile) {
        inheritanceLevelPerFile.forEach((name, value) -> System.out.println(name + ": " + value));
    }

    private Map<String, Integer> findSumOfInheritanceLevel(List<Class> classes) {
        
        Map<String, Integer> inheritanceLevelPerFile = new HashMap<>();
        
        for(Class clazz : classes) {
            inheritanceLevelPerFile.put(clazz.getName(), countLevelInheritanceRec(0, clazz));
        }

        return inheritanceLevelPerFile;
    }

    private int countLevelInheritanceRec(int count, Class clazz) {
        if(clazz.getSuperclass() != null && !(clazz.getSuperclass().equals(Object.class))) {
            count = countLevelInheritanceRec(count, clazz.getSuperclass()) + 1;
        }
        return count;
    }
}
