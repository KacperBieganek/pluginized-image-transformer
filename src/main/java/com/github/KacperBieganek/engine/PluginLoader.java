package com.github.KacperBieganek.engine;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PluginLoader extends ClassLoader {
    private static final String CLASS_EXTENSION = ".class";
    private static final String searchDirectoryName = "./out/production/classes/com/github/KacperBieganek/engine/plugins/impl";

    public PluginLoader(ClassLoader parent) {
        super(parent);
    }


    @Override
    public Class loadClass(String className) throws ClassNotFoundException {

        if (className.startsWith("com.github.KacperBieganek.engine.plugins.impl.") || classFileInDirectory(className)) {
            return getClass(className.replace("com.github.KacperBieganek.engine.plugins.impl.", ""));
        }
        return super.loadClass(className, true);
    }

    private boolean classFileInDirectory(String className) {
        File classesDir = new File(searchDirectoryName);
        File[] filesInDir = classesDir.listFiles();
        if (filesInDir != null) {
            for (File file : filesInDir) {
                if (file.getName().equals(className)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Class getClass(String className) throws ClassNotFoundException {
        byte[] b;
        try {
            className = className.replace(".class","");
            b = loadClassFileData(searchDirectoryName + File.separator + className + CLASS_EXTENSION);
            Class c = defineClass("com.github.KacperBieganek.engine.plugins.impl."+className, b, 0, b.length);
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
