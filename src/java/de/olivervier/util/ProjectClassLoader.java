package de.olivervier.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ProjectClassLoader {

    public ProjectClassLoader() {}

    public URLClassLoader getClassLoader (String pathToJar) throws Exception {
        
        if(pathToJar == null || pathToJar.isEmpty()) {
            throw new Exception("pathToJar is empty");
        }
        
        try {
            File jarFile = new File(pathToJar);
            URL[] urls = { new URL("jar:file:" + jarFile.getAbsolutePath() +"!/") };
            return URLClassLoader.newInstance(urls);        
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}