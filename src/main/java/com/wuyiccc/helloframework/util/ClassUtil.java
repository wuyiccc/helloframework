package com.wuyiccc.helloframework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wuyiccc
 * @date 2020/7/13 7:26
 * 岂曰无衣，与子同袍~
 */
@Slf4j
public class ClassUtil {

    public static final String FILE_PROTOCOL = "file";

    /**
     * 根据传入的包名，获取该包以及其子包下所有的类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {


        // 获取当前线程的类加载器
        ClassLoader classLoader = getClassLoader();
        // 通过类加载器获取到加载的资源
        URL url = classLoader.getResource(packageName.replace(".", "/")); // URL格式 类似于 file:/文件在本机上的绝对路径

        if (url == null) {
            log.warn("unable to retrieve anything from package: " + packageName);
            return null;
        }

        // 依据不同的资源，采用不同的方式获取资源的集合
        Set<Class<?>> classSet = null;

        // 过滤出文件类型的资源
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            classSet = new HashSet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        return classSet;

    }

    /**
     * 获取当前线程的上下文类加载器
     * @return 当前的类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


    private static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource, String packageName) {

        if (!fileSource.isDirectory()) { // 如果不是目录，直接返回
            return;
        }

        // 如果是文件夹，那么将该文件夹下的.class文件放入emptyClassSet中
        File[] files = fileSource.listFiles(new FileFilter() { // listFile 只会返回该文件夹下的文件及文件夹，不会返回子文件夹下的内容
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {

                    // 如果不是文件夹，就把.class文件放入类集合中
                    String absoluteFilePath = file.getAbsolutePath();
                    if (absoluteFilePath.endsWith(".class")) {
                        addToClassSet(absoluteFilePath);
                    }
                }

                return false;

            }

            private void addToClassSet(String absoluteFilePath) {

                // 从绝对路径下获取类名
                absoluteFilePath = absoluteFilePath.replace(File.separator, ".");
                String className = absoluteFilePath.substring(absoluteFilePath.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));// [)

                // 通过反射获取Class对象
                Class targetClass = loadClass(className);
                emptyClassSet.add(targetClass);
            }
        });

        if (files != null) {
            for (File f : files) {
                extractClassFile(emptyClassSet, f, packageName);
            }
        }
    }

    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error");
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化class
     *
     * @param clazz
     * @param accessible 是否可以通过反射来访问该class的private修饰的构造函数
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz, boolean accessible) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible); // 防止获取到的是private类型的构造函数
            return (T) constructor.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }

}
