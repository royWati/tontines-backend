package ekenya.co.ke.tontines.dao.repositories.jpql;

import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewMemberGroups {
    private MemberGroups memberGroups;
    private boolean inviteAccepted;
}
