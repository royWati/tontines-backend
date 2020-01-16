package ekenya.co.ke.tontines.dao.repositories.jpql;

import ekenya.co.ke.tontines.dao.entitites.ContributionSources;
import ekenya.co.ke.tontines.dao.entitites.Members;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author munialo.roy@ekenya.co.ke
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CumilativeContributionLogPerMember {

    private ContributionSources contributionSource;
    private Members groupMember;
    private long totalAmountContributed;
    private LocalDateTime lastContributionDate;

}
