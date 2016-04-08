package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by david.cai on 2016/4/6.
 */
public class ClassUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * @param className
     * @param isInitialize
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialize){
        Class<?> clz = null;
        try {
            clz = Class.forName(className, isInitialize, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class load failure", e);
            throw new RuntimeException(e);
        }

        return clz;
    }

    /**
     * 获取指定包名下所有类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classeSet = new HashSet<>();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if (url != null){
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)){
                        String packagePath = url.getPath().replace("%20", " ");
                        addClass(classeSet, packagePath, packageName);
                    } else if ("jar".equals(protocol)){

                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return classeSet;
    }

    private static void addClass(Set<Class<?>> classeSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return  ((file.isFile() && file.getName().endsWith("class")) ||
                        file.isDirectory());
            }
        });

        for (File file : files){
            String fileName = file.getName();
            if (file.isFile()){
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpyt(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classeSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpyt(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }

                String subPackageName = fileName;
                if (StringUtil.isNotEmpyt(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }

                addClass(classeSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classeSet, String className) {
        Class<?> clz = loadClass(className, false);
        classeSet.add(clz);
    }
}
