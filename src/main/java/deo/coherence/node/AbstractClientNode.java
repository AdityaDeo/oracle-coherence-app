package deo.coherence.node;

import deo.coherence.helpers.ClusterConfigHelper;

import java.io.IOException;

public abstract class AbstractClientNode extends AbstractClusterNode {

    @Override
    protected void beforeStartingCacheServer() throws IOException {
        ClusterConfigHelper.loadClientConfiguration();
    }
}
