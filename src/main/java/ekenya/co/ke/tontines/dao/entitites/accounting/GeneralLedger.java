package ekenya.co.ke.tontines.dao.entitites.accounting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;
import ekenya.co.ke.tontines.dao.entitites.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "acc_general_ledger")
@JsonIgnoreProperties(value = {"accountNumber"})
public class GeneralLedger extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "accountNumber", nullable = false)
    @JsonMerge
    @JsonProperty("accountNumber")

    private AccountNumber accountNumber;
    private String actualBalanceBefore;
    private String availableBalanceBefore;
    private String amount;
    @ManyToOne()
    @JoinColumn(name = "transactionType", nullable = false)
    @JsonMerge
    @JsonProperty("transactionType")
    private TransactionTypes transactionType;
    private String actualBalanceAfter;
    private String availableBalanceAfter;

    @ManyToOne()
    @JoinColumn(name = "transactionSource", nullable = false)
    @JsonMerge
    @JsonProperty("transactionSource")
    private TransactionsLog transactionSource;
}
