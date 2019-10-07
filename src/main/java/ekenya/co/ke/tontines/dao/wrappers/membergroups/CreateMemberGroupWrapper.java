package ekenya.co.ke.tontines.dao.wrappers.membergroups;

import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberGroupWrapper {

    private MemberGroups memberGroups;
    private List<MemberDetails> memberDetails;

}
