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
@Table(name = "account_balances")
public class AccountBalances extends BaseEntity {

    @ManyToOne()
    @JoinColumn(name = "accountNumber", nullable = false)
    @JsonMerge
    @JsonProperty("accountNumber")
    private AccountNumber accountNumber;
    private String actualBalance;
    private String availableBalance;
    private LocalDateTime updatedOn;

}
