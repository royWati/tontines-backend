package ekenya.co.ke.tontines.dao.repositories;

import ekenya.co.ke.tontines.dao.entitites.Contributions;
import ekenya.co.ke.tontines.dao.entitites.ContributionsLog;
import ekenya.co.ke.tontines.dao.repositories.jpql.GetGroupStatements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContributionLogRepository extends JpaRepository<ContributionsLog,Long> {

    String strGetGroupStatements = "SELECT NEW ekenya.co.ke.tontines.dao.repositories.jpql.GetGroupStatements" +
            "(" +
            "cl,c.name" +
            ") FROM ContributionsLog cl INNER JOIN Contributions c on cl.contribution = c.id INNER JOIN " +
            "MemberGroups mg ON c.memberGroup = mg.id WHERE mg.id = :membergroupId ";

    Page<ContributionsLog> findAllByContribution(Contributions id, Pageable pageable);


    @Query(strGetGroupStatements)
    Page<GetGroupStatements> getGroupStatements(@Param("membergroupId") long id,Pageable pageable);
}
