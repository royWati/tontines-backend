package ekenya.co.ke.tontines.dao.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMembersDetails {
    private boolean isRegistered;
    private boolean acceptedInvite;
    private long memberId;
    private boolean optOut;
}
