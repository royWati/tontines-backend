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
@Table(name = "account_mode")
public class AccountMode extends BaseEntity {
    @Column(name = "mode_type")
    private String mode;
    @Column(name = "operation_state")
    private boolean operationState;
}
