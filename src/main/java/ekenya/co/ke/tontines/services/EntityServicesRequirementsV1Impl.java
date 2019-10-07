package ekenya.co.ke.tontines.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import ekenya.co.ke.tontines.dao.entitites.Members;
import ekenya.co.ke.tontines.dao.entitites.Otp;
import ekenya.co.ke.tontines.dao.entitites.Region;
import ekenya.co.ke.tontines.dao.repositories.MembersRepository;
import ekenya.co.ke.tontines.dao.repositories.OtpRepository;
import ekenya.co.ke.tontines.dao.repositories.RegionsRepository;
import ekenya.co.ke.tontines.dao.wrappers.MemberGroupDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class EntityServicesRequirementsV1Impl implements EntityServicesRequirementsV1 {

    private final static Logger logger = Logger.getLogger(EntityServicesRequirementsV1Impl.class.getName());


    @Autowired
    private RegionsRepository regionsRepository;
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private OtpRepository otpRepository;
    @Override
    public List<Region> findAllRegions() {
        return regionsRepository.findAll();
    }

    @Override
    public List<Region> addAllRegions(List<Region> regionList) {
        return regionsRepository.saveAll(regionList);
    }

    @Override
    public Members getMemberDetails(long id) {
        List<Members> members = new ArrayList<>();
        membersRepository.findById(id).ifPresent(members::add);

        return members.size() > 0 ? members.get(0) : null;
    }

    @Override
    public List<Region> findAllDepartments() {
        return null;
    }

    @Override
    public List<Members> searchPhoneNumber(String phoneNumber) {


        return membersRepository.searchPhoneNumber(phoneNumber);
    }

    @Override
    public Members createMember(Members members) {
        return membersRepository.save(members);
    }

    @Override
    public Otp generateOtp(Otp otp) {
        return otpRepository.save(otp);
    }

    @Override
    public List<MemberGroupDetails> createMemberGroup(String s) {

        logger.info("string constructions..."+s);

        JsonArray jsonArray = new JsonParser().parse(s).getAsJsonArray();

        logger.info("string array..."+jsonArray.toString());

        List<MemberGroupDetails> data;
        Gson gson = new Gson();


        data = gson.fromJson(jsonArray, new TypeToken<List<MemberGroupDetails>>() {
        }.getType());

        try {
            logger.info(new ObjectMapper().writeValueAsString(data));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public List<Members> findMember(long id) {
      List<Members> membersList = membersRepository.findAllById(id);

      return membersList.size() > 0 ? membersList : new ArrayList<>();
    }
}
