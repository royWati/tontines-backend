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
@Table(name = "accounts_errored_transactions")
public class ErroredTransactions extends BaseEntity {

    @Column(name = "transaction_id")
    private long transactionId;
    @Column(name = "error_type")
    private String errorLog;
    @Column(name = "class_name")
    private String className;
    @Column(name = "method_name")
    private String methodName;
    @Column(name = "line_number")
    private String lineNumber;
    @Column(name = "originatorClass")
    private String originatorClass;
    @Column(name = "originatorMethod")
    private String originatorMethod;
    @Column(name = "fixed")
    private boolean fixed;
}
