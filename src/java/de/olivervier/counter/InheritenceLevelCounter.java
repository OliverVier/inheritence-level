package de.olivervier.counter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.olivervier.io.ClassFinder;

public class InheritenceLevelCounter {

    public Map<String, Integer> countInheritenceLevel(String basePath) {
        ClassFinder classFinder = new ClassFinder();
        List<Class> classes = classFinder.findClassesAtURI(basePath);
        return findSumOfInheritanceLevel(classes);
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
