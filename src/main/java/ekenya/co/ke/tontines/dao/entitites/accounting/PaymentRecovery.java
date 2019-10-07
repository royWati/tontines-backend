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
@Table(name = "accounts_payments_recovery")
public class PaymentRecovery extends BaseEntity {

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    @JsonMerge
    @JsonProperty("account_number")
    private AccountNumber accountNumber;
    @Column(name = "pending_amount")
    private String pendingAmount;
    @Column(name = "total_amount_recovered")
    private String totalAmountRecovered;
    @Column(name = "amount_to_recover")
    private String amountToRecover;
    @Column(name = "active_status")
    private boolean active;
}
