package deo.coherence.helpers;

import java.io.IOException;

public class ClusterConfigHelper {
    public static final String SERVER_PROPERTIES_FILE = "config\\properties\\coherence-server.properties";
    public static final String CLIENT_PROPERTIES_FILE = "config\\properties\\coherence-client.properties";
    public static final String COMMON_PROPERTIES_FILE = "config\\properties\\coherence-common.properties";

    public static void loadServerConfiguration() throws IOException {
        SystemPropertiesLoader.setSystemProperties(COMMON_PROPERTIES_FILE);
        SystemPropertiesLoader.setSystemProperties(SERVER_PROPERTIES_FILE);
    }

    public static void loadClientConfiguration() throws IOException {
        SystemPropertiesLoader.setSystemProperties(COMMON_PROPERTIES_FILE);
        SystemPropertiesLoader.setSystemProperties(CLIENT_PROPERTIES_FILE);
    }
}
