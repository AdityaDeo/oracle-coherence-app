package deo;

import deo.coherence.node.ClusterNode;
import deo.coherence.node.ClusterNodeFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;

import static org.apache.log4j.Logger.getLogger;

public class App {
    private static final Logger LOGGER = getLogger(App.class);
    private static final String NODE_TYPE_OPTION = "nodeType";
    private static final String NODE_ID_OPTION = "nodeId";

    public static void main(String[] args) throws Exception {
        new App().start(args);
    }

    private void start(String[] args) throws Exception {
        CommandLine cmd = new DefaultParser()
                .parse(getOptions(), args);

        if (!cmd.hasOption(NODE_TYPE_OPTION) || !cmd.hasOption(NODE_ID_OPTION)) {
            throw new IllegalArgumentException("Missing option(s)");
        }

        String nodeType = cmd.getOptionValue(NODE_TYPE_OPTION);
        String nodeId = cmd.getOptionValue(NODE_ID_OPTION);
        startNode(nodeType, nodeId);
    }

    private void startNode(String nodeType, String nodeId) throws Exception {
        ClusterNode node = ClusterNodeFactory.getNode(nodeType);
        node.start(nodeId);
        node.afterStart();
    }

    private Options getOptions() {
        return new Options()
            .addOption(NODE_TYPE_OPTION, true, "Cluster node type")
            .addOption(NODE_ID_OPTION, true, "Node id");
    }
}
