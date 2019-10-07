package ekenya.co.ke.tontines.dao.entitites.accounting;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import ekenya.co.ke.tontines.dao.entitites.BaseEntity;
import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "record_balance_tbl")
public class RecordBalance extends BaseEntity {
    private String accountName;
    private String accountNumber;
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "member_group", nullable = false)
    @JsonProperty("memberGroup")
    private MemberGroups memberGroup;

}
