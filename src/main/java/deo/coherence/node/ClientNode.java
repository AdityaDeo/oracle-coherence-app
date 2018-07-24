package deo.coherence.node;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import deo.coherence.helpers.ClusterConfigHelper;
import deo.domain.Transaction;

import java.io.IOException;

@DiscoverableByNodeFactory(nodeType = "client")
public class ClientNode extends AbstractClusterNode {

    @Override
    protected void beforeStartingCacheServer() throws IOException {
        ClusterConfigHelper.loadClientConfiguration();
    }

    @Override
    public void afterStart() throws Exception {
        CacheFactory.ensureCluster();

        NamedCache<String, Transaction> cache = CacheFactory.getCache("Transactions");

        Transaction transaction = new Transaction("Tr-1", "Ac-1", "Ac-2", 100);
        cache.put(transaction.produceKey(), transaction);

        getLogger().info( "Transaction: " + cache.get(transaction.produceKey()));
    }
}
