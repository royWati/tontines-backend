package ekenya.co.ke.tontines.services;

import ekenya.co.ke.tontines.dao.entitites.*;
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
    Page<Contributions> getAllContributions(Pageable pageable);
    Page<Contributions> getAllContributions(MemberGroups memberGroups,Pageable pageable);

    MemberAndGroupLink addMemberGroupLink(MemberAndGroupLink  memberAndGroupLink);
    Page<MemberAndGroupLink> getAllMembersInGroup(MemberGroups memberGroups ,Pageable pageable);


    List<MemberAndGroupLink> findMemberGroupAndMemberLink(MemberGroups memberGroups,Members members);
}
