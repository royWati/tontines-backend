package ekenya.co.ke.tontines.services;

import ekenya.co.ke.tontines.dao.entitites.*;
import ekenya.co.ke.tontines.dao.wrappers.*;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.CreateMemberGroupWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.LeaveGroupWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.MemberDetails;

import java.util.List;

public interface EntityManagementService {

    UniversalResponse CREATE_MEMBER(Members members);
    UniversalResponse UPDATE_MEMBER_PASSWORD(PasswordUpdater passwordUpdater);
    UniversalResponse UPDATE_MEMBER(Members members);
    UniversalResponse GET_MEMBER_DETAILS(long id);
    UniversalResponse VERIFY_OTP(VerifyOtpWrapper verifyOtpWrapper);

    Members getMemberDetails(String phoneNumber);

    UniversalResponse CREATE_MEMBERGROUP(CreateMemberGroupWrapper createMemberGroupWrapper);

    Members createUnregisteredMembers(MemberDetails memberDetails);
    List<Members> createUnregisteredMembers(List<MemberDetails> memberDetails);
    List<GroupMembersDetails> getGroupMemberDetails(List<Members> memberDetails);

    void LINK_MEMBER_TO_GROUP(Members members, MemberGroups  memberGroups,boolean isCreator);

    UniversalResponse GET_MEMBER_GROUP(long id);

    UniversalResponse VIEW_MEMBER_GROUP(long id);

    void SEND_OTP(Otp otp);

    UniversalResponse GET_ALL_MEMBERS_IN_GROUP(long id,int page, int size);

    UniversalResponse ADD_NEW_GROUP_MEMBER(List<MemberDetails> memberDetails,long id);

    UniversalResponse CREATE_CONTRIBUTION_GROUP(Contributions contributions);

    UniversalResponse GET_GROUP_CONTRIBUTIONS(long id,int page,int size);

    UniversalResponse MEMBER_LEAVE_GROUP(LeaveGroupWrapper leaveGroupWrapper);

    UniversalResponse GET_GROUP_ROLES();

    UniversalResponse GET_ALL_MEMBER_GROUPS(int page , int size);

}
