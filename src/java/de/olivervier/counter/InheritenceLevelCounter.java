package de.olivervier;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.olivervier.util.FileUtil;

public class InheritenceLevelCounter {

    private String basePath = null;

    public void execute(String rootPath) {

        if(rootPath == null || rootPath.isBlank()) {
            basePath = System.getProperty("java.class.path");
        } else {
            basePath = rootPath;
        }

        if(basePath == null) {
            System.err.println("Cannot get execution path");
            return;
        }

        var files = findFilesRec(basePath);
        var classes = findClassesOfFiles(files);
        var inheritanceLevelPerFile = findSumOfInheritanceLevel(classes);

        inheritanceLevelPerFile.forEach((name, value) -> System.out.println(name + ": " + value));
    }

    public List<File> findFilesRec(String uri) {

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

    public List<Class> findClassesOfFiles(List<File> files) {

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

    public Map<String, Integer> findSumOfInheritanceLevel(List<Class> classes) {
        
        Map<String, Integer> inheritanceLevelPerFile = new HashMap<>();
        
        for(Class clazz : classes) {
            inheritanceLevelPerFile.put(clazz.getName(), countLevelInheritanceRec(0, clazz));
        }

        return inheritanceLevelPerFile;
    }

    public int countLevelInheritanceRec(int count, Class clazz) {
        if(clazz.getSuperclass() != null && !(clazz.getSuperclass().equals(Object.class))) {
            count = countLevelInheritanceRec(count, clazz.getSuperclass()) + 1;
        }
        return count;
    }
}
