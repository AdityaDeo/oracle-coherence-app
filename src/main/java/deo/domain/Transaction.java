package deo.domain;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;
import com.tangosol.net.partition.KeyPartitioningStrategy;
import com.tangosol.net.partition.KeyPartitioningStrategy.PartitionAwareKey;
import deo.coherence.helpers.CacheUtils;

import static deo.coherence.Constants.CACHE_SERVICE_NAME;

@Portable
public class Transaction implements KeyProducer<String>, PartitionAwareKey {
    @PortableProperty(0)
    private String id;

    @PortableProperty(1)
    private String fromAccount;

    @PortableProperty(2)
    private String toAccount;

    @PortableProperty(3)
    private long amount;

    // POF
    @Deprecated
    public Transaction() { }

    public Transaction(String id, String fromAccount, String toAccount, long amount) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
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

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        if (amount != that.amount) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fromAccount != null ? !fromAccount.equals(that.fromAccount) : that.fromAccount != null) return false;
        return toAccount != null ? toAccount.equals(that.toAccount) : that.toAccount == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fromAccount != null ? fromAccount.hashCode() : 0);
        result = 31 * result + (toAccount != null ? toAccount.hashCode() : 0);
        result = 31 * result + (int) (amount ^ (amount >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public int getPartitionId() {
        return Integer.parseInt(id) / CacheUtils.getPartitionCount(CACHE_SERVICE_NAME);
    }
}
