package se.liu.ida.tdp024.account.data.impl.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import se.liu.ida.tdp024.account.data.api.entity.Account;

@Entity
public class AccountDB implements Account {

    @Version
    protected long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String personKey;
    private String accountType;
    private String bankKey;
    private int holdings;

    @OneToMany(mappedBy = "account", targetEntity = TransactionDB.class)
    @JsonBackReference
    private List<Transaction> transactions;

    /**
     * @return the id
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * @return the personKey
     */
    @Override
    public String getPersonKey() {
        return personKey;
    }

    /**
     * @param personKey the personKey to set
     */
    @Override
    public void setPersonKey(String personKey) {
        this.personKey = personKey;
    }

    /**
     * @return the accountType
     */
    @Override
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    @Override
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * @return the bankKey
     */
    @Override
    public String getBankKey() {
        return bankKey;
    }

    /**
     * @param bankKey the bankKey to set
     */
    @Override
    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    /**
     * @return the holdings
     */
    @Override
    public int getHoldings() {
        return holdings;
    }

    /**
     * @param holdings the holdings to set
     */
    @Override
    public void setHoldings(int holdings) {
        this.holdings = holdings;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
