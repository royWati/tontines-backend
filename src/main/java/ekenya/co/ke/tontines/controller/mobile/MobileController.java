package ekenya.co.ke.tontines.controller.mobile;

import com.sun.corba.se.spi.ior.ObjectKey;
import ekenya.co.ke.tontines.components.jwt.JwtTokenUtil;
import ekenya.co.ke.tontines.dao.entitites.Contributions;
import ekenya.co.ke.tontines.dao.entitites.ContributionsLog;
import ekenya.co.ke.tontines.dao.entitites.Members;
import ekenya.co.ke.tontines.dao.wrappers.*;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.RecordBalanceWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.*;
import ekenya.co.ke.tontines.services.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/mobile")
@ApiOperation(authorizations = {@Authorization(value = "Bearer ")},value = "all the endpoints are currently protected")
public class MobileController {

    private final static Logger logger = Logger.getLogger(MobileController.class.getName());

    @Autowired
    private EntityManagementService entityManagementService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    EntityManagementServiceV2 entityManagementServiceV2;


    @Autowired
    private JwtUserDetailsService userDetailsService;
    @PostMapping("/member")
    public Object addMember(@RequestBody Members members){
        return entityManagementService.CREATE_MEMBER(members);
    }
    @PutMapping("/member")
    public Object updateMember(@RequestBody Members members){
        return entityManagementService.UPDATE_MEMBER(members);
    }
    @PostMapping("/member/update-password")
    public Object updatePassword(@RequestBody PasswordUpdater passwordUpdater){
        return entityManagementService.UPDATE_MEMBER_PASSWORD(passwordUpdater);
    }
    @PostMapping("/member/verify-otp")
    public Object verifyOtp(@RequestBody VerifyOtpWrapper wrapper){
        return entityManagementService.VERIFY_OTP(wrapper);
    }

    @ApiOperation(value = "This endpoint is used to login the member to the mobile application" +
            "")
    @PostMapping("/authenticate")
    public Object authenticateUserDetails(@RequestBody  JwtRequest jwtRequest) throws Exception {

        logger.info("username ..."+jwtRequest.getUsername());
        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        Members m = entityManagementService.getMemberDetails(jwtRequest.getUsername());

        MobileLoginResponse mobileLoginResponse = new MobileLoginResponse();
        mobileLoginResponse.setAccess_token(new JwtResponse(token));
        mobileLoginResponse.setUserDetails(m);

        return new UniversalResponse(new Response(200,"token"),mobileLoginResponse);
    }

    @PostMapping("/member/details/{id}")
    public Object getMemberDetails(@PathVariable long id){
        return entityManagementService.GET_MEMBER_DETAILS(id);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS 56", e);
        }
    }

    @PostMapping("/member/groups")
    public Object getAllGroupsForMember(@RequestBody StatementGetWrapper statementGetWrapper){
        return entityManagementServiceV2.GET_GROUP_FOR_MEMBER(statementGetWrapper);
    }

    @PostMapping("/member/groups/invites")
    public Object getAllGroupsForMemberInvites(@RequestBody StatementGetWrapper statementGetWrapper){
        return entityManagementServiceV2.GET_GROUP_FOR_MEMBER_INVITES(statementGetWrapper);
    }

    @PostMapping("/member/groups/decline-invite")
    public Object declineGroupInvite(@RequestBody AcceptInviteWrapper acceptInviteWrapper){
        return entityManagementServiceV2.DECLINE_GROUP_INVITE(acceptInviteWrapper);
    }

    @PostMapping("/member/groups/add-role")
    public Object addMemberRole(@RequestBody AddRoleWrapper addRoleWrapper){
        return entityManagementServiceV2.ADD_MEMBER_ROLE(addRoleWrapper);
    }

    @PostMapping("/member/groups/accept-invite")
    public Object acceptGroupInvite(@RequestBody List<AcceptInviteWrapper> inviteWrapper){
        return entityManagementServiceV2.ACCEPT_GROUP_INVITE(inviteWrapper);
    }

    @PostMapping("/member/groups/send-invites")
    public Object sendExistingGroupInvites(@RequestBody CreateMemberGroupWrapper wrapper){
        return entityManagementService.SEND_NEW_INVITES(wrapper);
    }



    @PostMapping("/group/create")
    public Object createObject(@RequestBody CreateMemberGroupWrapper createMemberGroupWrapper){
        return entityManagementService.CREATE_MEMBERGROUP(createMemberGroupWrapper);
    }

    @PostMapping("/group")
    public Object findMemberGroup(@RequestBody StatementGetWrapper statementGetWrapper){
        return entityManagementService.VIEW_MEMBER_GROUP(statementGetWrapper.getId());
    }

    @PostMapping("/group/members")
    public Object findGroupMembers(@RequestBody StatementGetWrapper wrapper){
        return entityManagementService.GET_ALL_MEMBERS_IN_GROUP(wrapper.getId(), wrapper.getPage(),
                wrapper.getSize());
    }
    // TODO
    @PostMapping("/group/add-members/{id}")
    public Object addGroupMember(@RequestBody List<MemberDetails> memberDetails, @PathVariable long id){
        return entityManagementService.ADD_NEW_GROUP_MEMBER(memberDetails, id);
    }
    @PostMapping("/contribution/create")
    public Object createContribution(@RequestBody Contributions contributions){
        return entityManagementService.CREATE_CONTRIBUTION_GROUP(contributions);
    }
    @PostMapping("/group/contributions")
    public Object getGroupContributions(@RequestBody StatementGetWrapper wrapper){
        return entityManagementService.GET_GROUP_CONTRIBUTIONS(wrapper.getId(), wrapper.getPage(), wrapper.getSize());
    }

    @PostMapping("group/leave-group")
    public Object leaveGroup(@RequestBody LeaveGroupWrapper leaveGroup){
        return entityManagementService.MEMBER_LEAVE_GROUP(leaveGroup);
    }

    @PostMapping(value = "/group/upload-documents",consumes ="multipart/form-data")
    public Object uploadGroupDocuments(@RequestParam("files")MultipartFile[] files ,
                                       @RequestParam("groupId")String id, HttpServletRequest request){

        logger.info("/group/upload-documents request got here...");
        logger.info(request.getRequestURI());
        logger.info(request.getContentType());
        logger.info("group id --"+id);
        logger.info("files --"+files);
        logger.info("files size--"+files.length);


      Enumeration<String> En = request.getParameterNames();

     while (En.hasMoreElements()){
         logger.info("parameter name---"+ En.nextElement());
     }


     List<Part> partList = new ArrayList<>();

        try {
            for (Part p : request.getParts()){

                if (p.getSubmittedFileName() != null){
                    partList.add(p);
                }
                logger.info("submitted file name--"+p.getSubmittedFileName());
                p.getSize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        logger.info("total parts created..."+partList.size());

        return entityManagementServiceV2.UPLOAD_GROUP_DOCUMENTS(partList,Long.parseLong(id));
    }

    @PostMapping("/group/roles")
    public Object getGroupMemberRoles(){
        return entityManagementService.GET_GROUP_ROLES();
    }
    @PostMapping("/group/link-external-account")
    public Object linkExternalGroupAccount(@RequestBody RecordBalanceWrapper recordBalanceWrapper){
        return entityManagementServiceV2.RECORD_EXTERNAL_ACCOUNT(recordBalanceWrapper);
    }

    @PostMapping("/group/group-external-accounts")
    public Object getExternalGroupAccounts(@RequestBody StatementGetWrapper wrapper){
        return entityManagementServiceV2.GET_GROUP_EXTERNAL_ACCOUNTS(wrapper);
    }

    @PostMapping("/contribution/get-sources")
    public Object getContributionSources(){
        return entityManagementServiceV2.GET_CONTRIBUTION_SOURCES();
    }

    @PostMapping("/contribution/make-contribution")
    public Object makeContribution(@RequestBody ContributionsLog contributionsLog){
        return entityManagementServiceV2.MAKE_CONTRIBUTION(contributionsLog);
    }
    @PostMapping("/contribution")
    public Object getContributionInformation(@RequestBody StatementGetWrapper statementGetWrapper){
        return entityManagementServiceV2.GET_CONTRIBUTION_INFROMATION(statementGetWrapper.getId());
    }

    @PostMapping("/contribution/member-statements")
    public Object getTotalContributionsPerMember(@RequestBody StatementGetWrapper statementGetWrapper){
        return entityManagementServiceV2.GET_CUMILATIVE_CONTRIBUTION_PER_MEMBER(
                statementGetWrapper.getId(),statementGetWrapper.getPage(),statementGetWrapper.getSize()
        );
    }

    @PostMapping("/contribution/member-log")
    public Object getMemberContributionLog(@RequestBody StatementGetWrapper statementGetWrapper){

        return entityManagementServiceV2.GET_MEMBER_CONTRIBUTION_LOG(statementGetWrapper.getId(),
                statementGetWrapper.getContributionId(),statementGetWrapper.getPage(),statementGetWrapper.getSize());
    }

    @PostMapping("/contributions/statements")
    public Object contributionStatements(@RequestBody StatementGetWrapper wrapper){
        return entityManagementServiceV2.GET_CONTRIBUTION_STATEMENTS(wrapper);
    }
    @PostMapping("/group/statements")
    public Object groupStatements(@RequestBody StatementGetWrapper wrapper){
        return entityManagementServiceV2.GET_GROUP_STATEMENTS(wrapper);
    }
    @PostMapping("/record-statements")
    public Object recordExpenditures(){
        return null;
    }
    @PostMapping("/accounts/external-account-types")
    public Object getExternalAccountTypes(){
        return entityManagementServiceV2.GET_EXTERNAL_ACCOUNT_TYPES();
    }


}
