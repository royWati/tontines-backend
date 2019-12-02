package ekenya.co.ke.tontines.dao.wrappers.membergroups;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleWrapper {
    private long groupId;
    private long memberId;
    private long roleId;
}
