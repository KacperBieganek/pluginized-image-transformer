package com.github.KacperBieganek.engine;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PluginLoader extends ClassLoader {
    private final String CLASS_EXTENSION = ".class";
    public static final String searchDirectoryName = ".";

    public PluginLoader(ClassLoader parent) {
        super(parent);
    }


    @Override
    public Class loadClass(String className) throws ClassNotFoundException {

        if (className.startsWith("com.github.KacperBieganek") || classFileInDirectory(className)) {
            //return getClass(className.replace("com.github.KacperBieganek", ""));
            return getClass(className);
        }
        return super.loadClass(className, true);
    }

    private boolean classFileInDirectory(String className) {
        File classesDir = new File(searchDirectoryName);
        File[] filesInDir = classesDir.listFiles();
        if (filesInDir != null) {
            for (File file : filesInDir) {
                if (file.getName().equals(className + CLASS_EXTENSION)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Class getClass(String className) throws ClassNotFoundException {
        byte[] b;
        try {
            b = loadClassFileData(searchDirectoryName + File.separator + className + CLASS_EXTENSION);
            Class c = defineClass(className, b, 0, b.length);
            resolveClass(c);
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] loadClassFileData(String path) throws IOException {
        FileInputStream stream = new FileInputStream(path);
        int size = stream.available();
        byte buff[] = new byte[size];
        DataInputStream in = new DataInputStream(stream);
        in.readFully(buff);
        in.close();
        return buff;
    }
}
