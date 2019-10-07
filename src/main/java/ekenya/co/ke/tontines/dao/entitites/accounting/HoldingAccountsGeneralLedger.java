package ekenya.co.ke.tontines.dao.entitites.accounting;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;
import ekenya.co.ke.tontines.dao.entitites.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "acc_holding_general_ledger")
public class HoldingAccountsGeneralLedger extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "account_number", nullable = false)
    @JsonMerge
    @JsonProperty("account_number")
    private AccountNumber accountNumber;
    private String debitBalance;
    private String creditBalance;
    private LocalDateTime updateOn;
}
