package deo.coherence.node;

import deo.coherence.helpers.ClusterConfigHelper;

import java.util.concurrent.CountDownLatch;

@DiscoverableByNodeFactory(nodeType = "server")
public final class ServerNode extends AbstractClusterNode {

    @Override
    protected void beforeStartingCacheServer() throws Exception {
        ClusterConfigHelper.loadServerConfiguration();
    }

    @Override
    public void afterStart() throws InterruptedException {
        new CountDownLatch(1).await();
    }
}
