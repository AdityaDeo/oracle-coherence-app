package deo.coherence.node;

import com.tangosol.net.CacheFactory;

@DiscoverableByNodeFactory(nodeType = "queryConsoleNode")
public class QueryConsoleNode extends AbstractClientNode {

    @Override
    public void afterStart() throws Exception {
        CacheFactory.main(new String[0]);
    }
}