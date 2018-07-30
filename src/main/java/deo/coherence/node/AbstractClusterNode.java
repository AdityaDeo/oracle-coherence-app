package deo.coherence.node;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.DefaultCacheServer;
import com.tangosol.net.events.EventInterceptor;
import com.tangosol.net.events.application.LifecycleEvent;
import deo.coherence.helpers.MemberUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.tangosol.net.events.application.LifecycleEvent.Type.ACTIVATED;
import static com.tangosol.util.RegistrationBehavior.IGNORE;
import static java.lang.String.format;

public abstract class AbstractClusterNode implements ClusterNode {
    private final Logger logger;
    private boolean isNodeUp = false;

    protected abstract void beforeStartingCacheServer() throws Exception;

    public AbstractClusterNode() {
        try {
            logger = org.apache.log4j.Logger.getLogger(getClass());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public synchronized void start(String nodeId) {
        try {
            if (isNodeUp) {
                logger.info("Server is already up");
                return;
            }

            beforeStartingCacheServer();
            MemberUtils.setLocalMemberRole(nodeId);
            startCacheServer();
            isNodeUp = true;
            logger.info("Server started");
        } catch (Exception ex) {
            isNodeUp = false;
            logger.error("Error while starting server: {}", ex);
        }
    }

    @Override
    public synchronized void shutdown() {
        try {
            if (!isNodeUp) {
                return;
            }

            DefaultCacheServer.shutdown();
            logger.info("Server stopped");
        } catch (Exception ex) {
            logger.error("Error while stopping server: {}", ex);
        } finally {
            isNodeUp = false;
        }
    }

    private void startCacheServer() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        EventInterceptor<LifecycleEvent> interceptor = new AbstractClusterNode.ServerEventInterceptor(countDownLatch);
        ConfigurableCacheFactory configurableCacheFactory = CacheFactory.getConfigurableCacheFactory();
        configurableCacheFactory.getInterceptorRegistry().registerEventInterceptor(interceptor, IGNORE);

        /* internally starts services corresponding to cache-schemes */
        List startedServices = DefaultCacheServer.start();

        countDownLatch.await();
        logger.info(format("Started services: %s", startedServices));

        CacheFactory.ensureCluster();
    }

    private static class ServerEventInterceptor implements EventInterceptor<LifecycleEvent> {
        private CountDownLatch countDownLatch;

        public ServerEventInterceptor(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public void onEvent(LifecycleEvent event) {
            if (event.getType() == ACTIVATED) {
                countDownLatch.countDown();
            }
        }
    }
}
