package ekenya.co.ke.tontines.dao.entitites.accounting;

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
@Table(name = "acc_holding_accounts")
public class HoldingAccounts extends BaseEntity {

    @Column(name = "holding_account_name")
    private String accountName;
    @ManyToOne()
    @JoinColumn(name = "accountType", nullable = false)
    @JsonMerge
    @JsonProperty("accountType")
    private AccountType accountType;
}
