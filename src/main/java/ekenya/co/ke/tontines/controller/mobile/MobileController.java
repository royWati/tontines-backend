package ekenya.co.ke.tontines.controller.mobile;

import ekenya.co.ke.tontines.components.jwt.JwtTokenUtil;
import ekenya.co.ke.tontines.dao.entitites.Contributions;
import ekenya.co.ke.tontines.dao.entitites.Members;
import ekenya.co.ke.tontines.dao.wrappers.JwtRequest;
import ekenya.co.ke.tontines.dao.wrappers.JwtResponse;
import ekenya.co.ke.tontines.dao.wrappers.PasswordUpdater;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.RecordBalanceWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.CreateMemberGroupWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.LeaveGroupWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.MemberDetails;
import ekenya.co.ke.tontines.services.EntityManagementService;
import ekenya.co.ke.tontines.services.EntityManagementServiceV2;
import ekenya.co.ke.tontines.services.EntityServicesRequirementsV2;
import ekenya.co.ke.tontines.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mobile")
public class MobileController {
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

    @PostMapping("/authenticate")
    public ResponseEntity authenticateUserDetails(@RequestBody JwtRequest jwtRequest) throws Exception {

        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
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
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/group/create")
    public Object createObject(@RequestBody CreateMemberGroupWrapper createMemberGroupWrapper){
        return entityManagementService.CREATE_MEMBERGROUP(createMemberGroupWrapper);
    }

    @PostMapping("/group/{id}")
    public Object findMemberGroup(@PathVariable long id){
        return entityManagementService.VIEW_MEMBER_GROUP(id);
    }

    @PostMapping("/group/members/{id}")
    public Object findGroupMembers(@PathVariable long id,
                                   @RequestParam int page, @RequestParam int size){
        return entityManagementService.GET_ALL_MEMBERS_IN_GROUP(id, page, size);
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
    @PostMapping("/group/contributions/{id}")
    public Object getGroupContributions(@PathVariable long id,@RequestParam int page, @RequestParam int size){
        return entityManagementService.GET_GROUP_CONTRIBUTIONS(id, page, size);
    }

    @PostMapping("group/leave-group")
    public Object leaveGroup(@RequestBody LeaveGroupWrapper leaveGroup){
        return entityManagementService.MEMBER_LEAVE_GROUP(leaveGroup);
    }

    @PostMapping("/group/link-external-account")
    public Object linkExternalGroupAccount(@RequestBody RecordBalanceWrapper recordBalanceWrapper){
        return entityManagementServiceV2.RECORD_EXTERNAL_ACCOUNT(recordBalanceWrapper);
    }
}
