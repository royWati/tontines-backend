package ekenya.co.ke.tontines.services;

import ekenya.co.ke.tontines.dao.entitites.*;
import ekenya.co.ke.tontines.dao.repositories.ContributionsRepository;
import ekenya.co.ke.tontines.dao.repositories.MemberAndGroupLinkRepository;
import ekenya.co.ke.tontines.dao.repositories.MemberGroupsRepository;
import ekenya.co.ke.tontines.dao.repositories.appconfigs.ContributionTypesRepository;
import ekenya.co.ke.tontines.dao.repositories.appconfigs.GroupTypesRepository;
import ekenya.co.ke.tontines.dao.repositories.appconfigs.IndivualContributionsRepository;
import ekenya.co.ke.tontines.dao.repositories.appconfigs.ScheduleTypesRepository;
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
    public List<MemberAndGroupLink> findMemberGroupAndMemberLink(MemberGroups memberGroups, Members members) {
        return memberAndGroupLinkRepository.findAllByMemberGroupAndMemberAndSoftDelete(memberGroups, members,false);
    }


}
