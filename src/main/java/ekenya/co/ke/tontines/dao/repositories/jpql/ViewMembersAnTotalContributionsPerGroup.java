package ekenya.co.ke.tontines.dao.repositories.jpql;

import ekenya.co.ke.tontines.dao.entitites.Members;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author munialo.roy@ekenya.co.ke
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewMembersAnTotalContributionsPerGroup {

    private Members member;
    private Integer totalContribution;
}
