package ekenya.co.ke.tontines.dao.repositories.jpql;

import ekenya.co.ke.tontines.dao.entitites.ContributionsLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupStatements {
    private ContributionsLog memberStatement;
    private String contributionName;
}
