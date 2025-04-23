package de.olivervier.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.olivervier.util.FileUtil;

public class ClassFinder {
    
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

    public List<Class> findClassesAtURI(String uri) {

        List<File> files = findFilesRec(uri);

        List<Class> classes = new ArrayList<>();

        for (File file : files) {

            String fileName = FileUtil.getRelativePath(uri, file.getAbsolutePath());
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

}
