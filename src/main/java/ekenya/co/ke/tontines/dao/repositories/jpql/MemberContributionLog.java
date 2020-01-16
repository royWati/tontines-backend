package ekenya.co.ke.tontines.dao.repositories.jpql;

import ekenya.co.ke.tontines.dao.entitites.ContributionSources;
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
public class MemberContributionLog {

    private Long id;
    private LocalDateTime createdOn;
    private Long amountContributed;
    private ContributionSources contributionSource;
}
