package deo.coherence.node;

public interface ClusterNode {
    void start(String nodeId) throws Exception;
    void afterStart() throws Exception;
    void shutdown() throws Exception;
}
