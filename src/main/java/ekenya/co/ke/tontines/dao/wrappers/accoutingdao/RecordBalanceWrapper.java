package ekenya.co.ke.tontines.dao.wrappers.accoutingdao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordBalanceWrapper {

    private String accountName;
    private String accountNumber;
    private long groupId;
    private String amount;
}
