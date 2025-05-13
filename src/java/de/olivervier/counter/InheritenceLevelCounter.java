package de.olivervier.counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.olivervier.io.ClassFinder;

public class InheritenceLevelCounter {

    public Map<String, Integer> countLevelPerClass(String basePath) {
        ClassFinder classFinder = new ClassFinder();
        List<Class<?>> classes = classFinder.findClassesAtURI(basePath);
        return countLevelPerClass(classes);
    }

    public Map<Integer, Integer> countClassesPerLevel(String basePath) {
        ClassFinder classFinder = new ClassFinder();
        List<Class<?>> classes = classFinder.findClassesAtURI(basePath);
        return countClassesPerLevel(classes);
    }

    private Map<String, Integer> countLevelPerClass(List<Class<?>> classes) {
        
        Map<String, Integer> inheritanceLevelPerFile = new HashMap<>();
        
        for(Class<?> clazz : classes) {
            inheritanceLevelPerFile.put(clazz.getName(), countLevelPerClassRec(0, clazz));
        }

        return inheritanceLevelPerFile;
    }

    //Returns the amount of classes in a inheritence level 
    private Map<Integer, Integer> countClassesPerLevel(List<Class<?>> classes) {
        Map<Integer, Integer> amountOfClassesPerLevel = new HashMap<>();
        for(Class<?> clazz : classes) {
            int level = countLevelPerClassRec(0, clazz);
            if(amountOfClassesPerLevel.containsKey(level)) {
                amountOfClassesPerLevel.put(level, amountOfClassesPerLevel.get(level)+1);
            } else {
                amountOfClassesPerLevel.put(level, 1);
            }
        }
        return amountOfClassesPerLevel;
    }

    private int countLevelPerClassRec(int count, Class<?> clazz) {
        if(clazz.getSuperclass() != null && !(clazz.getSuperclass().equals(Object.class))) {
            count = countLevelPerClassRec(count, clazz.getSuperclass()) + 1;
        }
        return count;
    }
}
