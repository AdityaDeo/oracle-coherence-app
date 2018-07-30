package deo.coherence.helpers;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.DistributedCacheService;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class CacheUtils {
    public static DistributedCacheService getCacheService(String serviceNam) {
        return (DistributedCacheService) CacheFactory.getService(serviceNam);
    }

    public static List<String> getCacheNamesForService(String serviceName) {
        DistributedCacheService distributedCacheService = (DistributedCacheService)CacheFactory.getService(serviceName);
        Enumeration<String> enumeration = distributedCacheService.getCacheNames();
        List<String> cacheNames = Collections.list(enumeration);
        return cacheNames;
    }

    public static int getPartitionCount(String serviceName) {
        DistributedCacheService distributedCacheService = (DistributedCacheService)CacheFactory.getService(serviceName);
        return distributedCacheService.getPartitionCount();
    }
}
