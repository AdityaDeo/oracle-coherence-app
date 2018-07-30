package deo.coherence.aggregators;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.net.BackingMapManagerContext;
import com.tangosol.net.DistributedCacheService;
import com.tangosol.net.partition.KeyPartitioningStrategy.PartitionAwareKey;
import com.tangosol.util.Binary;
import com.tangosol.util.BinaryEntry;
import com.tangosol.util.Converter;
import com.tangosol.util.InvocableMap;
import deo.coherence.helpers.CacheUtils;
import deo.coherence.helpers.MemberUtils;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.Map.Entry;

import static deo.coherence.Constants.CACHE_SERVICE_NAME;
import static java.lang.String.format;
import static org.apache.log4j.Logger.getLogger;

@Portable
public class PartitionInspectingAggregator implements InvocableMap.ParallelAwareAggregator {
    private static final Logger LOGGER = getLogger(PartitionInspectingAggregator.class);

    @Override
    public InvocableMap.EntryAggregator getParallelAggregator() {
        return this;
    }

    @Override
    public Object aggregateResults(Collection collection) {
        return null;
    }

    @Override
    public Object aggregate(Set set) {
        int logKey = new Random().nextInt(899) + 100;
        String messageFormat = format("logKey [%s], member [%s], message [%s]", logKey, MemberUtils.getLocalMemberInfo(), "%s");
        LOGGER.info(format(messageFormat, "Started"));

        DistributedCacheService cacheService = CacheUtils.getCacheService(CACHE_SERVICE_NAME);
        BackingMapManagerContext managerContext = cacheService.getBackingMapManager().getContext();
        List<String> cacheNames = CacheUtils.getCacheNamesForService(CACHE_SERVICE_NAME);
        Converter<Binary, PartitionAwareKey> keyConverter = managerContext.getKeyFromInternalConverter();
        Converter<Binary, PartitionAwareKey> valueConverter = managerContext.getValueFromInternalConverter();

        for (BinaryEntry paramEntries : (Set<BinaryEntry>)set) {

            for (String cacheName : cacheNames) {

                for(Entry dataEntry : (Set<Entry>)managerContext.getBackingMapContext(cacheName).getBackingMap().entrySet()) {

                    Object key = keyConverter.convert((Binary)dataEntry.getKey());
                    Object value = valueConverter.convert((Binary)dataEntry.getValue());
                    int partition = ((PartitionAwareKey)paramEntries.getKey()).getPartitionId();
                    LOGGER.info(format(messageFormat, format("Partition [%s], Cache [%s], Data entry [[%s], [%s]]", partition, cacheName, key, value)));

                }

            }

        }

        LOGGER.info(format(messageFormat, "Finished"));
        return null;
    }
}
