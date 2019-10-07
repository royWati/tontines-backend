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
@Table(name = "account_numbers")
public class AccountNumber extends BaseEntity {

    @Column(name = "unique_table_id")
    private long tableIdentifier;
    @Column(name = "table_name")
    private String tableName;
    @ManyToOne()
    @JoinColumn(name = "accountType", nullable = false)
    @JsonMerge
    @JsonProperty("accountType")
    private AccountType accountType;
    @ManyToOne()
    @JoinColumn(name = "accountMode", nullable = false)
    @JsonMerge
    @JsonProperty("accountMode")
    private AccountMode accountMode;
    private String accRefNumber;
}
