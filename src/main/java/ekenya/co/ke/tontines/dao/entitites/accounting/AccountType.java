package ekenya.co.ke.tontines.dao.entitites.accounting;

import ekenya.co.ke.tontines.dao.entitites.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_types")
public class AccountType extends BaseEntity {
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "account_prefix")
    private String accountPrefix;

}
