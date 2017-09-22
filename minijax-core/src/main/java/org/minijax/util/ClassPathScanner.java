package org.minijax.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * https://stackoverflow.com/a/22462785/2051724
 */
public class ClassPathScanner {
    private static final Logger LOG = LoggerFactory.getLogger(ClassPathScanner.class);
    private final Set<Class<?>> result = new HashSet<>();


    public static Set<Class<?>> scan(final String packageName) throws IOException {
        return scan(packageName, Thread.currentThread().getContextClassLoader());
    }


    public static Set<Class<?>> scan(final String packageName, final ClassLoader cld)
            throws IOException {
        final ClassPathScanner scanner = new ClassPathScanner();
        scanner.scanImpl(packageName, cld);
        return scanner.result;
    }


    private void scanImpl(final String packageName, final ClassLoader cld) throws IOException {
        final Enumeration<URL> resources = cld.getResources(packageName.replace('.', '/'));

        while (resources.hasMoreElements()) {
            final URL url = resources.nextElement();
            final String protocol = url.getProtocol();

            if (protocol.equals("file")) {
                checkDirectory(new File(URLDecoder.decode(url.getPath(), "UTF-8")), packageName);
            } else if (protocol.equals("jar")) {
                checkJarFile(url, packageName);
            } else {
                throw new IllegalArgumentException("Unrecognized classpath protocol: " + protocol);
            }
        }
    }


    private void checkDirectory(final File file, final String name) {
        if (!file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            for (final File child : file.listFiles()) {
                checkDirectory(child, name + "." + child.getName());
            }

        } else if (file.getName().endsWith(".class")) {
            addClass(name.substring(0, name.length() - 6));
        }
    }


    private void checkJarFile(final URL url, final String pckgname) throws IOException {
        final JarURLConnection conn = (JarURLConnection) url.openConnection();
        final JarFile jarFile = conn.getJarFile();
        final Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            final JarEntry jarEntry = entries.nextElement();

            String name = jarEntry.getName();

            if (name.endsWith(".class")) {
                name = name.substring(0, name.length() - 6).replace('/', '.');

                if (name.startsWith(pckgname)) {
                    addClass(name);
                }
            }
        }
    }


    private void addClass(final String className) {
        try {
            result.add(Class.forName(className));
        } catch (final ClassNotFoundException | NoClassDefFoundError ex) {
            LOG.error("Class error: {}", ex.getMessage(), ex);
        }
    }
}
