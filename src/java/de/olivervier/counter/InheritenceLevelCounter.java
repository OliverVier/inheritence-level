package de.olivervier.counter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.olivervier.util.FileUtil;

public class InheritenceLevelCounter {

    public void execute(String basePath) {

        if(basePath == null) {
            System.err.println("Cannot get execution path");
            return;
        }

        List<File> files = findFilesRec(basePath);
        List<Class> classes = findClassesOfFiles(basePath, files);
        Map<String, Integer> inheritanceLevelPerFile = findSumOfInheritanceLevel(classes);

        printInheritanceLevelPerFile(inheritanceLevelPerFile);
    }

    private void printInheritanceLevelPerFile(Map<String, Integer> inheritanceLevelPerFile) {
        inheritanceLevelPerFile.forEach((name, value) -> System.out.println(name + ": " + value));
    }

    private List<File> findFilesRec(String uri) {

        List<File> files = new ArrayList<File>();
        
        File basepathFile = new File(uri);
        
        if(basepathFile.isFile()) {
            files.add(basepathFile);
        }

        if(basepathFile.listFiles() == null) {
            return files;
        }
		
        for(File file : basepathFile.listFiles()) {
            String fileExtension = FileUtil.getFileExtension(file);

            if(fileExtension == null) {
                files.addAll(findFilesRec(file.getAbsolutePath()));
            }

            if(fileExtension != null && FileUtil.getFileExtension(file).equals(".java")) {				
                files.add(file);
            }
        }
		
        return files;
    }

    private List<Class> findClassesOfFiles(String basePath, List<File> files) {

        List<Class> classes = new ArrayList<>();

        for (File file : files) {

            String fileName = FileUtil.getRelativePath(basePath, file.getAbsolutePath());
            String convertedPackageName = FileUtil.convertFolderToPackageName(fileName);

            try {
                Class clazz = Class.forName(convertedPackageName);
                classes.add(clazz);
            } catch (ClassNotFoundException e) {
                System.out.println("Could not find class under given name " + convertedPackageName);
            } catch (ExceptionInInitializerError e) {
                e.printStackTrace();
            }
        }

        return classes;
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
