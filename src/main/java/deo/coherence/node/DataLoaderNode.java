package deo.coherence.node;

import deo.coherence.service.DataLoadService;

@DiscoverableByNodeFactory(nodeType = "dataLoaderNode")
public class DataLoaderNode extends AbstractClientNode {

    @Override
    public void afterStart() throws Exception {
        DataLoadService.loadSampleTransactions();
        DataLoadService.loadSampleCustomers();
    }
}
