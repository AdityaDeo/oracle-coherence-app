package deo.coherence.helpers;

import deo.coherence.node.ClusterNode;
import deo.coherence.node.DiscoverableByNodeFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassUtils {
    private static final String PACKAGE_DELIMITER = ".";
    private static final String CLASS_FILE_EXTENSION = ".class";
    private static final String INNER_CLASS_DELIMITER = "$";

    public static Map<String, ClusterNode> getClusterNodeInstances() throws ClassNotFoundException, IOException, URISyntaxException, IllegalAccessException, InstantiationException {
        List<Class> clusterNodeClasses = getClassesWithAnnotation(ClusterNode.class, DiscoverableByNodeFactory.class);
        Map<String, ClusterNode> result = new HashMap<>();

        for (Class clazz : clusterNodeClasses) {
            if(!ClusterNode.class.isAssignableFrom(clazz)) {
                continue;
            }

            String nodeType = ((DiscoverableByNodeFactory)clazz.getAnnotation(DiscoverableByNodeFactory.class)).nodeType();
            ClusterNode node = (ClusterNode)clazz.newInstance();
            result.put(nodeType, node);
        }

        return result;
    }

    public static List<Class> getClassesWithAnnotation(Class basePackageClass, Class annotationClass) throws URISyntaxException, IOException, ClassNotFoundException {
        List<Class> result = new ArrayList<>();

        for (Class candidate : getClasses(basePackageClass)) {
            if (candidate.isAnnotationPresent(annotationClass)) {
                result.add(candidate);
            }
        }

        return  result;
    }

    public static List<Class> getClasses(Class basePackageClass) throws ClassNotFoundException, IOException, URISyntaxException {
        ClassLoader classLoader = basePackageClass.getClassLoader();
        String basePackage = basePackageClass.getPackage().getName();

        List<Class> classes = new ArrayList<>();
        String baseDirectory = classLoader.getResource(basePackage.replace(PACKAGE_DELIMITER, File.separator)).toURI().getPath();
        String[] candidates = new File(baseDirectory).list();

        for (String candidate : candidates) {
            File file = new File(candidate);
            String fileName = file.getName();

            if(!file.isDirectory()
                    && fileName.endsWith(CLASS_FILE_EXTENSION)
                    && !fileName.contains(INNER_CLASS_DELIMITER)) {

                String className = file.getName().replace(CLASS_FILE_EXTENSION, "");
                classes.add(Class.forName(basePackage + PACKAGE_DELIMITER + className));
            }
        }

        return classes;
    }
}
