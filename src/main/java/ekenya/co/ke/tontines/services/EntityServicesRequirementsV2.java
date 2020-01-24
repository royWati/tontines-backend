package ekenya.co.ke.tontines.services;

import ekenya.co.ke.tontines.dao.entitites.*;
import ekenya.co.ke.tontines.dao.entitites.accounting.ExternalAccountTypes;
import ekenya.co.ke.tontines.dao.repositories.jpql.*;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * contains entities for scheduleTypes,indiviualContributionTypes, ContributionsTypes, GroupTypes
 *
 * MemberGroups, Contributions
 */
public interface EntityServicesRequirementsV2 {

    List<ScheduleTypes> getAllScheduleTypes();
    ScheduleTypes createScheduleType(ScheduleTypes scheduleTypes);

    List<IndivualContributionTypes> getIndiviualContributionTypes();
    IndivualContributionTypes createIndivualContributionTypes(IndivualContributionTypes indivualContributionTypes);

    List<GroupTypes> getGroupTypes();
    GroupTypes createGroupTpe(GroupTypes groupTypes);

    List<ContributionType> getContributionTypes();
    ContributionType createIndivualContributionTypes(ContributionType contributionType);

    Page<MemberGroups> getAllMemberGroups(Pageable pageable);

    MemberGroups createMemberGroup(MemberGroups members);

    List<MemberGroups> findMemberGroups(long id);

    Contributions createContribution(Contributions contributions);
    List<Contributions> getContributionById(long id);
    Page<Contributions> getAllContributions(Pageable pageable);
    Page<Contributions> getAllContributions(MemberGroups memberGroups,Pageable pageable);

    MemberAndGroupLink addMemberGroupLink(MemberAndGroupLink  memberAndGroupLink);
    Page<MemberAndGroupLink> getAllMembersInGroup(MemberGroups memberGroups ,Pageable pageable);
    Page<ViewMemberGroups> getMemberGroupsForMember(long id,Pageable pageable);
    Page<ViewMemberGroups> getMemberGroupsForMemberInvites(long id,Pageable pageable);


    List<MemberAndGroupLink> checkMemberInvite(MemberGroups memberGroups,Members members);


    List<MemberAndGroupLink> findMemberGroupAndMemberLink(MemberGroups memberGroups,Members members);

    ContributionsLog addContributionLog(ContributionsLog contributionsLog);
    Page<ContributionsLog> getContributionsLogs(Contributions id, Pageable pageable);
    Page<GetGroupStatements> getGroupContributions(long id,Pageable pageable);

    List<MemberRoles> getMemberRoles();

    DocumentsLibrary createDocumentDirectory(DocumentsLibrary documentsLibrary);

    List<ExternalAccountTypes> getExternalAccountTypes();

    Page<ViewMembersAnTotalContributionsPerGroup>
    getGroupMembers(MemberGroups memberGroups,Pageable pageable);

    Page<CumilativeContributionLogPerMember> getCumilativeContributionPerMember(Contributions contributions,
                                                                                Pageable pageable);


    Page<MemberContributionLog> getMemberContributionLog(Members members, Contributions contributions, Pageable pageable);
}
