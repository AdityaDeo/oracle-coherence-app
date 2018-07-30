package deo.coherence.processors;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.util.InvocableMap;
import com.tangosol.util.InvocableMap.EntryProcessor;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;

import static org.apache.log4j.Logger.getLogger;

@Portable
public class CustomerRenamingProcessor implements EntryProcessor {
    private static final Logger LOGGER = getLogger(CustomerRenamingProcessor.class);

    @Override
    public Object process(InvocableMap.Entry entry) {
        LOGGER.info("process method called");
        return null;
    }

    @Override
    public Map processAll(Set setEntries) {
        LOGGER.info("processAll method called");

        int numEntries = setEntries.size();

        return null;
    }
}
