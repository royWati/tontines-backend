package ekenya.co.ke.tontines.services;


import ekenya.co.ke.tontines.dao.entitites.Members;
import ekenya.co.ke.tontines.dao.entitites.Otp;
import ekenya.co.ke.tontines.dao.entitites.Region;
import ekenya.co.ke.tontines.dao.wrappers.MemberGroupDetails;

import java.util.List;

/**
 * contans entity operations for members, regions, otp, departments
 */
public interface EntityServicesRequirementsV1 {

    List<Region> findAllRegions();
    List<Region> addAllRegions(List<Region> regionList);
    Members getMemberDetails(long id);
    List<Region> findAllDepartments();

    List<Members> searchPhoneNumber(String phoneNumber);

    Members createMember(Members members);
    Otp generateOtp(Otp otp);

    List<MemberGroupDetails> createMemberGroup(String s);

    List<Otp> findOtp(String otpValue , Members members);

    List<Members> findMember(long id);
}
