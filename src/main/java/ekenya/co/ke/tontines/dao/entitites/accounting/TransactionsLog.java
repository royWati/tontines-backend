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
@Table(name = "account_transactions_log")
public class TransactionsLog extends BaseEntity {

    @Column(name = "unique_transaction_id")
    private long uniqueTransactionId;
    @Column(name = "table_name")
    private String tableName;
    @Column(name = "narration")
    private String narration;
}
