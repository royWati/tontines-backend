package ekenya.co.ke.tontines.dao.repositories;

import ekenya.co.ke.tontines.dao.entitites.Contributions;
import ekenya.co.ke.tontines.dao.entitites.ContributionsLog;
import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import ekenya.co.ke.tontines.dao.entitites.Members;
import ekenya.co.ke.tontines.dao.repositories.jpql.CumilativeContributionLogPerMember;
import ekenya.co.ke.tontines.dao.repositories.jpql.GetGroupStatements;
import ekenya.co.ke.tontines.dao.repositories.jpql.MemberContributionLog;
import ekenya.co.ke.tontines.dao.repositories.jpql.ViewMembersAnTotalContributionsPerGroup;
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

    String strGetContributionsPerGroup = "SELECT NEW ekenya.co.ke.tontines.dao." +
            "repositories.jpql.ViewMembersAnTotalContributionsPerGroup(" +
            "m.member,COALESCE(0,SUM(cl.amount))" +
            ")" +
            " FROM MemberAndGroupLink m LEFT JOIN" +
            "  ContributionsLog cl ON cl.member = m.id " +
            " " +
            "WHERE m.memberGroup = :memberGroup GROUP BY m.member";

    String strGetContributionStatementInformation = "SELECT NEW ekenya.co.ke.tontines.dao." +
            "repositories.jpql.CumilativeContributionLogPerMember(" +
            "cl.contributionSources," +
            "cl.member," +
            "SUM(cl.amount)," +
            "cl.createdOn" +
            ") FROM ContributionsLog cl WHERE cl.contribution = :contribution group by cl.member";

    String strGetMemberContributionLog = "SELECT NEW ekenya.co.ke.tontines.dao.repositories.jpql." +
            "MemberContributionLog(" +
            "cl.id," +
            "cl.createdOn," +
            "cl.amount," +
            "cl.contributionSources" +
            ") FROM ContributionsLog cl WHERE cl.member = :member AND cl.contribution = :contribution";

    Page<ContributionsLog> findAllByContribution(Contributions id, Pageable pageable);


    @Query(strGetGroupStatements)
    Page<GetGroupStatements> getGroupStatements(@Param("membergroupId") long id,Pageable pageable);

    @Query(strGetContributionsPerGroup)
    Page<ViewMembersAnTotalContributionsPerGroup> getGroupMembersAndTotalContributions(
            @Param("memberGroup")MemberGroups memberGroups,Pageable pageable
            );

    @Query(strGetContributionStatementInformation)
    Page<CumilativeContributionLogPerMember> getCumilativeContributionPerMember(@Param("contribution")
                                                                                Contributions contributions,
                                                                                Pageable pageable);
    @Query(strGetMemberContributionLog)
    Page<MemberContributionLog> getMemberContributionLog(@Param("member")Members members,
                                                         @Param("contribution") Contributions contribution,
                                                         Pageable pageable);
}
