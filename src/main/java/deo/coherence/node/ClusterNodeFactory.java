package deo.coherence.node;

import org.apache.log4j.Logger;

import java.util.Map;

import static deo.coherence.helpers.ClassUtils.getClusterNodeInstances;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.apache.log4j.Logger.getLogger;

public class ClusterNodeFactory {
    private static final Logger logger = getLogger(ClusterNodeFactory.class);
    private static Map<String, ClusterNode> nodes;

    public static ClusterNode getNode(String strNodeType) throws Exception {
        ClusterNode node = getNodes().get(strNodeType);
        return requireNonNull(node, "Node not found for given type");
    }

    private static Map<String, ClusterNode> getNodes() throws Exception {
        if (nodes == null) {
            synchronized (ClusterNodeFactory.class) {
                if (nodes == null) {
                    nodes = getClusterNodeInstances();
                    logger.info(format("Initialized nodes with [%s]", nodes));
                }
            }
        }

        return nodes;
    }
}
