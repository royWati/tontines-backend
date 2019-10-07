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
@Table(name = "funds_out_transaction_to_pg")
public class FundsOutTransactionToPG extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "user")
    @JsonMerge
    @JsonProperty("account_number")
    private AccountNumber accountNumber;
    private long amountToPG;
    private long charges;
    @ManyToOne()
    @JoinColumn(name = "wallet_service")
    @JsonMerge
    @JsonProperty("wallet_service")
    private WalletServices walletService;
    private boolean processed;
    private boolean successfulTransaction;
    private String transactionId;
    private String recipientAccount;
}
