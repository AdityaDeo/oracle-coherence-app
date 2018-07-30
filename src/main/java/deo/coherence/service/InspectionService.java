package deo.coherence.service;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.partition.KeyPartitioningStrategy;
import com.tangosol.net.partition.SimplePartitionKey;
import com.tangosol.util.filter.AlwaysFilter;
import deo.coherence.CacheName;
import deo.coherence.aggregators.PartitionInspectingAggregator;
import deo.coherence.helpers.CacheUtils;
import deo.coherence.processors.CustomerRenamingProcessor;

import java.util.HashSet;
import java.util.Set;

import static deo.coherence.Constants.CACHE_SERVICE_NAME;

public class InspectionService {

    public static void runPartitionInspectingAggregator(CacheName cacheName) {
        NamedCache cache = CacheFactory.getCache(cacheName.name());
        cache.aggregate(keyForEachPartition(), new PartitionInspectingAggregator());
    }

    public static void runEntryProcessor(CacheName cacheName) {
        NamedCache cache = CacheFactory.getCache(cacheName.name());
        cache.invoke(AlwaysFilter.INSTANCE, new CustomerRenamingProcessor());
    }

    private static Set<KeyPartitioningStrategy.PartitionAwareKey> keyForEachPartition() {
        int numPartitions = CacheUtils.getPartitionCount(CACHE_SERVICE_NAME);
        Set<KeyPartitioningStrategy.PartitionAwareKey> result = new HashSet<>();

        for (int i = 0; i < numPartitions; i++) {
            result.add(SimplePartitionKey.getPartitionKey(i));
        }

        return result;
    }
}
