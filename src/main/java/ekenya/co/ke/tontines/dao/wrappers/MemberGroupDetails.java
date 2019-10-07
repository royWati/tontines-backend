package ekenya.co.ke.tontines.dao.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberGroupDetails {
    private long groupId;
    private boolean isActive;
}
