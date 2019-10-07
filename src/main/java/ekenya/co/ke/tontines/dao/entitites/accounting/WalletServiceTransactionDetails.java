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
@Table(name = "wallet_service_transaction_details")
public class WalletServiceTransactionDetails extends BaseEntity {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "walletService", nullable = false)
    @JsonMerge
    @JsonProperty("walletServices")
    private WalletServices walletServices;
    @Column(name = "minimum_amount")
    private long minimumAmount;
    @Column(name = "maximum_amount")
    private long maximumAmount;
    @Column(name = "charge")
    private long charge;
    @Column(name = "excise_duty")
    private double exciseDuty;
    @Column(name = "total_amount")
    private double totalAmount;
    @Column(name = "processing_fee")
    private long processingFee;
}
