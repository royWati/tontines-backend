package ekenya.co.ke.tontines.dao.entitites.accounting;

import ekenya.co.ke.tontines.dao.entitites.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.TableGenerator;


/**
 * credit and debit
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TableGenerator(name = "account_transaction_type")
public class TransactionTypes extends BaseEntity {
    @Column(name = "transaction_type")
    private String name;
}
