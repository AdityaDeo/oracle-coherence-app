package deo.coherence.helpers;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

public class SystemPropertiesLoader {
    private static final Logger LOGGER = Logger.getLogger(SystemPropertiesLoader.class);

    public static void setSystemProperties(String file) throws IOException {
        InputStream inputStream = SystemPropertiesLoader.class.getClassLoader().getResourceAsStream(file);
        Properties properties = new Properties();
        properties.load(inputStream);
        setSystemProperties(properties);
    }

    public static synchronized void setSystemProperties(Properties properties) {
        Properties current = System.getProperties();
        LOGGER.info(format("About to add system properties. Current properties [%s]. Properties to be added [%s]", current, properties));
        current.putAll(properties);
        LOGGER.info(format("Added system properties. Current properties [%s]", current));
    }
}
