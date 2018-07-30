package deo.domain;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;
import com.tangosol.net.partition.KeyPartitioningStrategy;
import com.tangosol.net.partition.KeyPartitioningStrategy.PartitionAwareKey;
import deo.coherence.helpers.CacheUtils;

import static deo.coherence.Constants.CACHE_SERVICE_NAME;

@Portable
public class Customer implements KeyProducer<String>, PartitionAwareKey {

    @PortableProperty(0)
    private String id;

    @PortableProperty(1)
    private String name;

    @Deprecated
    public Customer() {
    }

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String produceKey() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (id != null ? !id.equals(customer.id) : customer.id != null) return false;
        return name != null ? name.equals(customer.name) : customer.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int getPartitionId() {
        return Integer.parseInt(id) / CacheUtils.getPartitionCount(CACHE_SERVICE_NAME);
    }
}
