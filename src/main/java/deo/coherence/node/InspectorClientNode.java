package deo.coherence.node;

import deo.coherence.CacheName;
import deo.coherence.service.InspectionService;

@DiscoverableByNodeFactory(nodeType = "inspectorClientNode")
public class InspectorClientNode extends AbstractClientNode {

    @Override
    public void afterStart() throws Exception {
        InspectionService.runEntryProcessor(CacheName.TRANSACTIONS);
    }
}
