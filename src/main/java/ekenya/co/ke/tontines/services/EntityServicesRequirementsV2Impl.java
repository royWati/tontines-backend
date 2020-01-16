package ekenya.co.ke.tontines.services;

import ekenya.co.ke.tontines.dao.entitites.*;
import ekenya.co.ke.tontines.dao.entitites.accounting.ExternalAccountTypes;
import ekenya.co.ke.tontines.dao.repositories.*;
import ekenya.co.ke.tontines.dao.repositories.accounting.ExternalAccountTypesRepository;
import ekenya.co.ke.tontines.dao.repositories.appconfigs.ContributionTypesRepository;
import ekenya.co.ke.tontines.dao.repositories.appconfigs.GroupTypesRepository;
import ekenya.co.ke.tontines.dao.repositories.appconfigs.IndivualContributionsRepository;
import ekenya.co.ke.tontines.dao.repositories.appconfigs.ScheduleTypesRepository;
import ekenya.co.ke.tontines.dao.repositories.jpql.*;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntityServicesRequirementsV2Impl implements EntityServicesRequirementsV2 {

    @Autowired
    private IndivualContributionsRepository indivualContributionsRepository;
    @Autowired
    private GroupTypesRepository groupTypesRepository;
    @Autowired
    private ScheduleTypesRepository scheduleTypesRepository;

    @Autowired
    private ContributionTypesRepository contributionTypesRepository;

    @Autowired
    private MemberGroupsRepository memberGroupsRepository;

    @Autowired
    private ContributionsRepository contributionsRepository;

    @Autowired
    private MemberAndGroupLinkRepository memberAndGroupLinkRepository;

    @Autowired private ContributionLogRepository contributionLogRepository;

    @Autowired private MemberRolesRepository memberRolesRepository;

    @Autowired private DocumentsLibraryRepository documentsLibraryRepository;
    @Autowired private ExternalAccountTypesRepository externalAccountTypesRepository;
    @Override
    public List<ScheduleTypes> getAllScheduleTypes() {
        return scheduleTypesRepository.findAll();
    }

    @Override
    public ScheduleTypes createScheduleType(ScheduleTypes scheduleTypes) {
        return scheduleTypesRepository.save(scheduleTypes);
    }

    @Override
    public List<IndivualContributionTypes> getIndiviualContributionTypes() {
        return indivualContributionsRepository.findAll();

    }

    @Override
    public IndivualContributionTypes createIndivualContributionTypes(IndivualContributionTypes indivualContributionTypes) {

        return indivualContributionsRepository.save(indivualContributionTypes);
    }

    @Override
    public List<GroupTypes> getGroupTypes() {
        return groupTypesRepository.findAll();
    }

    @Override
    public GroupTypes createGroupTpe(GroupTypes groupTypes) {
        return groupTypesRepository.save(groupTypes);
    }

    @Override
    public List<ContributionType> getContributionTypes() {
        return contributionTypesRepository.findAll();
    }

    @Override
    public ContributionType createIndivualContributionTypes(ContributionType contributionType) {
        return contributionTypesRepository.save(contributionType);
    }

    @Override
    public Page<MemberGroups> getAllMemberGroups(Pageable pageable) {
        return memberGroupsRepository.findAllBySoftDelete(false,pageable);
    }

    @Override
    public MemberGroups createMemberGroup(MemberGroups members) {
        return memberGroupsRepository.save(members);
    }

    @Override
    public List<MemberGroups> findMemberGroups(long id) {
        List<MemberGroups> list = memberGroupsRepository.findAllByIdAndSoftDelete(id,false);

        return list.size() > 0 ? list : new ArrayList<>();

    }

    @Override
    public Contributions createContribution(Contributions contributions) {
        return contributionsRepository.save(contributions);
    }

    @Override
    public List<Contributions> getContributionById(long id) {
        List<Contributions> list =  contributionsRepository.findAllByIdAndSoftDelete(id,false);
        return list.size() > 0 ? list : new ArrayList<>();
    }

    @Override
    public Page<Contributions> getAllContributions(Pageable pageable) {
        return contributionsRepository.findAllBySoftDelete(false,pageable);
    }

    @Override
    public Page<Contributions> getAllContributions(MemberGroups memberGroups,Pageable pageable) {
        return contributionsRepository.findAllByMemberGroupAndSoftDelete(memberGroups,false,pageable);
    }

    @Override
    public MemberAndGroupLink addMemberGroupLink(MemberAndGroupLink memberAndGroupLink) {
        return memberAndGroupLinkRepository.save(memberAndGroupLink);
    }

    @Override
    public Page<MemberAndGroupLink> getAllMembersInGroup(MemberGroups memberGroups, Pageable pageable) {
        return memberAndGroupLinkRepository.findAllByMemberGroupAndSoftDelete(memberGroups, pageable,false);
    }

    @Override
    public Page<ViewMemberGroups> getMemberGroupsForMember(long id,Pageable pageable) {
        Members m = new Members();
        m.setId(id);
        return memberAndGroupLinkRepository.loadMemberGroupsForMember(m,pageable);
    }

    @Override
    public Page<ViewMemberGroups> getMemberGroupsForMemberInvites(long id, Pageable pageable) {
        Members m = new Members();
        m.setId(id);
        return memberAndGroupLinkRepository.loadMemberGroupsForMemberInvites(m,pageable);
    }

    @Override
    public List<MemberAndGroupLink> findMemberGroupAndMemberLink(MemberGroups memberGroups, Members members) {
        return memberAndGroupLinkRepository.findAllByMemberGroupAndMemberAndSoftDelete(memberGroups, members,false);
    }

    @Override
    public ContributionsLog addContributionLog(ContributionsLog contributionsLog) {
        return contributionLogRepository.save(contributionsLog);
    }

    @Override
    public Page<ContributionsLog> getContributionsLogs(Contributions id, Pageable pageable) {
        return contributionLogRepository.findAllByContribution(id, pageable);
    }

    @Override
    public Page<GetGroupStatements> getGroupContributions(long id, Pageable pageable) {
        return contributionLogRepository.getGroupStatements(id, pageable);
    }

    @Override
    public List<MemberRoles> getMemberRoles() {
        return memberRolesRepository.findAll();
    }

    @Override
    public DocumentsLibrary createDocumentDirectory(DocumentsLibrary documentsLibrary) {
        return documentsLibraryRepository.save(documentsLibrary);
    }

    @Override
    public List<ExternalAccountTypes> getExternalAccountTypes() {
        return externalAccountTypesRepository.findAll();
    }

    @Override
    public Page<ViewMembersAnTotalContributionsPerGroup> getGroupMembers(MemberGroups memberGroups,
                                                                         Pageable pageable) {
        return contributionLogRepository.getGroupMembersAndTotalContributions(memberGroups,pageable);
    }

    @Override
    public Page<CumilativeContributionLogPerMember> getCumilativeContributionPerMember(Contributions contributions, Pageable pageable) {
        return contributionLogRepository.getCumilativeContributionPerMember(contributions,pageable);
    }

    @Override
    public Page<MemberContributionLog> getMemberContributionLog(Members members, Contributions contributions, Pageable pageable) {
        return contributionLogRepository.getMemberContributionLog(members, contributions, pageable);
    }
}
