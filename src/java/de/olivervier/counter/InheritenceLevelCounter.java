package de.olivervier.counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.olivervier.io.ClassFinder;

public class InheritenceLevelCounter {

    public Map<String, Integer> countInheritenceLevel(String basePath) {
        ClassFinder classFinder = new ClassFinder();
        List<Class<?>> classes = classFinder.findClassesAtURI(basePath);
        return findSumOfInheritanceLevel(classes);
    }

    public Map<Integer, Integer> groupByInheritenceLevel(String basePath) {
        ClassFinder classFinder = new ClassFinder();
        List<Class<?>> classes = classFinder.findClassesAtURI(basePath);
        return groupByInheritanceLevel(classes);
    }

    private Map<String, Integer> findSumOfInheritanceLevel(List<Class<?>> classes) {
        
        Map<String, Integer> inheritanceLevelPerFile = new HashMap<>();
        
        for(Class<?> clazz : classes) {
            inheritanceLevelPerFile.put(clazz.getName(), countLevelInheritanceRec(0, clazz));
        }

        return inheritanceLevelPerFile;
    }

    //Returns the amount of classes in a inheritence level 
    private Map<Integer, Integer> groupByInheritanceLevel(List<Class<?>> classes) {
        Map<Integer, Integer> amountOfClassesPerLevel = new HashMap<>();
        for(Class<?> clazz : classes) {
            int level = countLevelInheritanceRec(0, clazz);
            if(amountOfClassesPerLevel.containsKey(level)) {
                amountOfClassesPerLevel.put(level, amountOfClassesPerLevel.get(level)+1);
            } else {
                amountOfClassesPerLevel.put(level, 1);
            }
        }
        return amountOfClassesPerLevel;
    }

    private int countLevelInheritanceRec(int count, Class<?> clazz) {
        if(clazz.getSuperclass() != null && !(clazz.getSuperclass().equals(Object.class))) {
            count = countLevelInheritanceRec(count, clazz.getSuperclass()) + 1;
        }
        return count;
    }
}
