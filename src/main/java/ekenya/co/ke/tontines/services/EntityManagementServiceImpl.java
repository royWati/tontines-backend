package ekenya.co.ke.tontines.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ekenya.co.ke.tontines.dao.entitites.*;
import ekenya.co.ke.tontines.dao.entitites.accounting.AccountNumber;
import ekenya.co.ke.tontines.dao.entitites.accounting.AccountType;
import ekenya.co.ke.tontines.dao.wrappers.*;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.CreateMemberGroupWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.LeaveGroupWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.MemberDetails;
import ekenya.co.ke.tontines.services.accounting.AccountingService;
import ekenya.co.ke.tontines.services.accounting.AccountingServiceImpl;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Service
public class EntityManagementServiceImpl implements EntityManagementService {
    private final static Logger logger = Logger.getLogger(EntityManagementServiceImpl.class.getName());


    @Autowired
    private EntityServicesRequirementsV1 entityServicesRequirementsV1;
    @Autowired
    private EntityServicesRequirementsV2 entityServicesRequirementsV2;

    @Autowired
    private AccountingService accountingService;
    /**
     *
     * @param members
     * @return members to be returned as a result
     *
     * add member - > generate OTP - > sendOTP to user phone number
     */
    @Override
    public UniversalResponse CREATE_MEMBER(Members members) {

        List<MemberGroupDetails> memberGroup = new ArrayList<>();
        String strMemberGroups = objectMapperConverter(memberGroup);

        members.setMemberGroups(strMemberGroups);
        members.setRegisteredMember(true);
        members.setMemberGroups("[]");
        Members createdMember = entityServicesRequirementsV1.createMember(members);



        Otp otp = new Otp();
        otp.setMembers(createdMember);
        otp.setOtpType("REGISTRATION");
        Otp createdOtp = entityServicesRequirementsV1.generateOtp(otp);
        SEND_OTP(createdOtp);

  //      System.out.println(createdMember.getRegion().getName());

        Response response = new Response(200,"member created successfully. OTP has been sent " +
                "to the provided phone number");


        // create account number
        AccountType accountType = accountingService.findAccountType("MA").get(0);

        AccountNumber accountNumber = accountingService.createAccountNumber(createdMember.getId(),
                Members.class.getSimpleName(),accountType);

        logger.info("account number created successsfully..."+accountNumber.getId());

        return new UniversalResponse(response,createdMember);
    }

    @Override
    public UniversalResponse UPDATE_MEMBER_PASSWORD(PasswordUpdater passwordUpdater) {

        Members members = entityServicesRequirementsV1.getMemberDetails(passwordUpdater.getId());

        if (members != null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String p = passwordEncoder.encode(passwordUpdater.getPassword());

            members.setPassword(p);
            members.setPendingRegistrationStatus(false);
            Members updateMember  = entityServicesRequirementsV1.createMember(members);

            Response response = new Response(200,"member updated successfully. ");

            return new UniversalResponse(response,updateMember);
        }else{
            Response response = new Response(404,"member id not found ");
            return new UniversalResponse(response,null);
        }


    }

    @Override
    public UniversalResponse UPDATE_MEMBER(Members members) {
        Response response = new Response(200,"member updated successfully.");
        return new UniversalResponse(response,entityServicesRequirementsV1.createMember(members));
    }

    @Override
    public UniversalResponse GET_MEMBER_DETAILS(long id) {

        Members member = entityServicesRequirementsV1.getMemberDetails(id);

        if (member !=null){
            Response response = new Response(200,"member details retrieved successfully");
            return new UniversalResponse(response,member);
        }else{
            Response response = new Response(404,"member not found");
            return new UniversalResponse(response,null);
        }
    }

    @Override
    public UniversalResponse CREATE_MEMBERGROUP(CreateMemberGroupWrapper createMemberGroupWrapper) {


        logger.info(objectMapperConverter(createMemberGroupWrapper));
        System.out.println("awesome...");
        System.out.println("group type..."+createMemberGroupWrapper.getMemberGroups().getGroupType().getId());
        MemberGroups memberGroups = createMemberGroupWrapper.getMemberGroups();



        List<Members> membersList =createUnregisteredMembers(createMemberGroupWrapper.getMemberDetails());

        List<GroupMembersDetails> groupMembersDetailsList = getGroupMemberDetails(membersList);

        String strMembers = objectMapperConverter(groupMembersDetailsList);

     //   memberGroups.setGroupMembers(strMembers);

        MemberGroups mg = entityServicesRequirementsV2.createMemberGroup(memberGroups);

        membersList.forEach(members -> {


            LINK_MEMBER_TO_GROUP(members,mg);


//            List<MemberGroupDetails> memberGroupDetails = entityServicesRequirementsV1.createMemberGroup(
//                    members.getMemberGroups()
//            );
//
//            MemberGroupDetails d = new MemberGroupDetails();
//            d.setActive(true);
//            d.setGroupId(mg.getId());
//
//            memberGroupDetails.add(d);
//
//            String strMemberGroups = objectMapperConverter(memberGroupDetails);
//
//            members.setMemberGroups(strMemberGroups);

   //         entityServicesRequirementsV1.createMember(members);
        });


        AccountType type = accountingService.findAccountType("CA").get(0);
        accountingService.createAccountNumber(mg.getId(),MemberGroups.class.getSimpleName(),type);


        return new UniversalResponse(new Response(200,"Group members created successfully"),
                mg);
    }

    @Override
    public Members createUnregisteredMembers(MemberDetails memberDetails) {
        Members member = new Members();
        member.setPhoneNumber(memberDetails.getPhoneNumber());
        member.setRegisteredMember(false);
        member.setFirstName(memberDetails.getContactName());
        member.setMemberGroups("[]");
        Department d = new Department();
        d.setId(1);
        member.setDepartment(d);

        return entityServicesRequirementsV1.createMember(member);

    }

    @Override
    public List<Members> createUnregisteredMembers(List<MemberDetails> memberDetails) {

        List<Members> membersList = new ArrayList<>();

        memberDetails.forEach(memberDetails1 -> {
            String s =  memberDetails1.getPhoneNumber().replaceAll("[\\s+&,-]","");
            Character zero = s.charAt(0);
            String p = memberDetails1.getPhoneNumber();
            if (zero.equals('0')){
                p = s.substring(1);
            }

            List<Members> li = entityServicesRequirementsV1.searchPhoneNumber(p);

            if (li.size() > 0){
                membersList.add(li.get(0));
            } else{
                Members m = createUnregisteredMembers(memberDetails1);
                membersList.add(m);
            }
        });


        return membersList;
    }

    @Override
    public List<GroupMembersDetails> getGroupMemberDetails(List<Members> memberDetails) {

        List<GroupMembersDetails> list = new ArrayList<>();

        memberDetails.forEach(members -> {
            GroupMembersDetails d = new GroupMembersDetails();
            d.setAcceptedInvite(false);
            d.setMemberId(members.getId());
            d.setOptOut(false);
            d.setRegistered(members.isRegisteredMember());

            list.add(d);
        });
        return list;
    }


    // TODO :: validate where the user already belongs to the the group before adding the user to the group
    @Override
    public void LINK_MEMBER_TO_GROUP(Members members, MemberGroups memberGroups) {
        MemberAndGroupLink memberAndGroupLink = new MemberAndGroupLink();

        memberAndGroupLink.setMember(members);
        memberAndGroupLink.setMemberGroup(memberGroups);
        memberAndGroupLink.setHasAcceptedInvite(false);

        entityServicesRequirementsV2.addMemberGroupLink(memberAndGroupLink);
    }

    @Override
    public UniversalResponse GET_MEMBER_GROUP(long id) {
        Response response = new Response(200,"member group retrived successfully");
        Response response404 = new Response(404,"member group not found");

        List<MemberGroups> list = entityServicesRequirementsV2.findMemberGroups(id);

        return list.size() > 0 ? new UniversalResponse(response,list) : new UniversalResponse(response404,list);
    }


    @Override
    public UniversalResponse VIEW_MEMBER_GROUP(long id) {

        List<MemberGroups> list = entityServicesRequirementsV2.findMemberGroups(id);

        if (list.size() > 0){
            return new UniversalResponse(new Response(200,"member group retrieved successfully"),
                    list.get(0));
        }else{
            return new UniversalResponse(new Response(404,"member group not found"));
        }

    }

    @Override
    public void SEND_OTP(Otp otp) {

        String code = otp.getMembers().getCountyCode();
        String phone =otp.getMembers().getPhoneNumber();
        String msisdn = new StringBuilder().append(code).append(phone).toString();
        System.out.println("otp request recieved..."+msisdn);
    }

    public String objectMapperConverter(Object object){
        String data=null;
        try {
             data = new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public UniversalResponse GET_ALL_MEMBERS_IN_GROUP(long id, int page, int size) {

        List<MemberGroups> list = entityServicesRequirementsV2.findMemberGroups(id);

        if (list.size() > 0){

            MemberGroups memberGroups = list.get(0);

            Pageable pageable = PageRequest.of(page, size);

            Page<MemberAndGroupLink> memberAndGroupLinkList = entityServicesRequirementsV2.getAllMembersInGroup(
                    memberGroups,pageable
            );

            return new UniversalResponse(new Response(200,"members found"),memberAndGroupLinkList);

        }else{
            return new UniversalResponse(new Response(404,"member group not found"),list);
        }

    }

    @Override
    public UniversalResponse ADD_NEW_GROUP_MEMBER(List<MemberDetails> memberDetails,long id) {

        List<Members> membersList =createUnregisteredMembers(memberDetails);

        List<MemberGroups> list = entityServicesRequirementsV2.findMemberGroups(id);


        if (list.size() > 0 ){
            MemberGroups m = list.get(0);
            membersList.forEach(members -> LINK_MEMBER_TO_GROUP(members,m));

            return new UniversalResponse(new Response(200,"members added successfully"));
        }else{
            return new UniversalResponse(new Response(404,"member group not found"));
        }

    }

    @Override
    public UniversalResponse CREATE_CONTRIBUTION_GROUP(Contributions contributions) {
        Contributions c = entityServicesRequirementsV2.createContribution(contributions);

        return new UniversalResponse(new Response(200,"contribution created successfully"),
                c);
    }

    @Override
    public UniversalResponse GET_GROUP_CONTRIBUTIONS(long id,int page,int size) {
        List<MemberGroups> list = entityServicesRequirementsV2.findMemberGroups(id);

        if (list.size() > 0 ){

            Pageable p = PageRequest.of(page, size);
            MemberGroups m = list.get(0);
            Page<Contributions> contributionsPage = entityServicesRequirementsV2.getAllContributions(m,p);

            return new UniversalResponse(new Response(200,"group contributions retrieved successfully"),
                    contributionsPage);
        }else{
            return new UniversalResponse(new Response(404, "group not found"));
        }

    }

    @Override
    public UniversalResponse MEMBER_LEAVE_GROUP(LeaveGroupWrapper leaveGroupWrapper) {
        List<MemberGroups> mgList =
                entityServicesRequirementsV2.findMemberGroups(leaveGroupWrapper.getGroupId());

        if (mgList.size() > 0 ){

            List<Members> mList = entityServicesRequirementsV1.findMember(leaveGroupWrapper.getMemberId());

            if (mList.size() > 0){

                List<MemberAndGroupLink> links = entityServicesRequirementsV2.findMemberGroupAndMemberLink(
                        mgList.get(0),mList.get(0)
                );

                if (links.size() > 0 ){

                    MemberAndGroupLink memberAndGroupLink = links.get(0);

                    memberAndGroupLink.setSoftDelete(true);

                    MemberAndGroupLink mLink = entityServicesRequirementsV2.addMemberGroupLink(memberAndGroupLink);

                    logger.info(mLink.getId()+" deleted successfully...");

                    return new UniversalResponse(new Response(200,"member has left the group successfully"));
                }else{
                    return new UniversalResponse(new Response(406,"user not linked to group"));
                }

            }else {
                return new UniversalResponse(new Response(405,"user not found"));
            }

        }else{
            return new UniversalResponse(new Response(404,"member group not found"));
        }

    }
}