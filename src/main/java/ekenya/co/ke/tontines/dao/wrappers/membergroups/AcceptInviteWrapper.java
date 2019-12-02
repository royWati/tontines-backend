package ekenya.co.ke.tontines.dao.wrappers.membergroups;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcceptInviteWrapper {
    private long groupId;
    private long memberId;
}
