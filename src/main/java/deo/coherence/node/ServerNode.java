package deo.coherence.node;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.DefaultCacheServer;
import com.tangosol.net.events.EventInterceptor;
import com.tangosol.net.events.application.LifecycleEvent;
import deo.coherence.helpers.ClusterConfigHelper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.tangosol.net.events.application.LifecycleEvent.Type.ACTIVATED;
import static com.tangosol.util.RegistrationBehavior.IGNORE;
import static java.lang.String.format;
import static org.apache.log4j.Logger.getLogger;

@DiscoverableByNodeFactory(nodeType = "server")
public final class ServerNode extends AbstractClusterNode {

    @Override
    protected void beforeStartingCacheServer() throws Exception {
        ClusterConfigHelper.loadServerConfiguration();
    }

    @Override
    public void afterStart() throws InterruptedException {
        CacheFactory.ensureCluster();
        new CountDownLatch(1).await();
    }
}
