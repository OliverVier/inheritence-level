package de.olivervier.io;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import de.olivervier.util.FileUtil;
import de.olivervier.util.ProjectClassLoader;

public class ClassFinder {
    
    private List<String> findFilesInJAR(String uri){

        try (JarFile jarFile = new JarFile(new File(uri))) {
            List<String> filePathsInJAR = new ArrayList<>();
            Iterator<JarEntry> iterator = jarFile.entries().asIterator();
            
            while(iterator.hasNext()) {
                JarEntry entry = iterator.next();
                String entryFilePath = entry.getName();
                
                String entryFileExtension = FileUtil.getFileExtension(entryFilePath);
                                                    
                if(entryFilePath.contains("$") || entryFileExtension == null 
                                                 || !entryFileExtension.toLowerCase()
                                                                       .equals(".class")) {
                    continue;
                }
                filePathsInJAR.add(entry.getName());
            }

            return filePathsInJAR;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Class<?>> findClassesAtURI(String uri) {

        List<String> files = findFilesInJAR(uri);
        URLClassLoader classLoader;

        try {
            classLoader = new ProjectClassLoader().getClassLoader(uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        List<Class<?>> classes = new ArrayList<>();

        for (String relativeFilePath : files) {
            
            String convertedPackageName = FileUtil.convertFolderToPackageName(relativeFilePath);
            
            Class<?> clazz = null;

            try {
                clazz = classLoader.loadClass(convertedPackageName);
                classes.add(clazz);
            } 
            catch (ClassNotFoundException e) {} 
            catch (NoClassDefFoundError e) {} 
            catch (Exception e) {}
        }
        
        return classes;
    }
}