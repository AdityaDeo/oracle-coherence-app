package deo.coherence.service;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import deo.coherence.CacheName;
import deo.coherence.helpers.CacheUtils;
import deo.domain.Customer;
import deo.domain.Transaction;
import org.apache.log4j.Logger;

import static deo.coherence.Constants.CACHE_SERVICE_NAME;
import static org.apache.log4j.Logger.getLogger;

public class DataLoadService {
    private static final Logger LOGGER = getLogger(DataLoadService.class);

    public static void loadSampleTransactions() {
        int numPartitions = CacheUtils.getPartitionCount(CACHE_SERVICE_NAME);

        NamedCache transactionsCache = CacheFactory.getCache(CacheName.TRANSACTIONS.name());
        for (int i = 1; i <=  numPartitions * 5; i++) {
            Transaction transaction = new Transaction(i + "", "From-Ac-" + i, "To-Ac-" + i, i * 1000);
            transactionsCache.put(transaction.produceKey(), transaction);
        }

        LOGGER.info("Transactions loaded");
    }

    public static void loadSampleCustomers() {
        int numPartitions = CacheUtils.getPartitionCount(CACHE_SERVICE_NAME);

        NamedCache customersCache = CacheFactory.getCache(CacheName.CUSTOMERS.name());
        for (int i = 1; i <=  numPartitions * 5; i++) {
            Customer customer = new Customer(i + "", "Customer-" + i);
            customersCache.put(customer.produceKey(), customer);
        }

        LOGGER.info("Customers loaded");
    }
}
