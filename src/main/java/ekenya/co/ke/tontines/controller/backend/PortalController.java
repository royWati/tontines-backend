package ekenya.co.ke.tontines.controller.backend;

import ekenya.co.ke.tontines.components.jwt.JwtTokenUtil;
import ekenya.co.ke.tontines.dao.entitites.portal.Privileges;
import ekenya.co.ke.tontines.dao.entitites.portal.Roles;
import ekenya.co.ke.tontines.dao.entitites.portal.SystemUsers;
import ekenya.co.ke.tontines.dao.repositories.portal.SystemUserRepository;
import ekenya.co.ke.tontines.dao.wrappers.JwtRequest;
import ekenya.co.ke.tontines.dao.wrappers.JwtResponse;
import ekenya.co.ke.tontines.dao.wrappers.StatementGetWrapper;
import ekenya.co.ke.tontines.services.portal.AccessControlService;
import ekenya.co.ke.tontines.services.portal.PortalAuthentification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portal/access")
public class PortalController {

    @Autowired
    private PortalAuthentification portalAuthentification;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AccessControlService accessControlService;


    @Autowired @Lazy
    private SystemUserRepository systemUserRepository;
    @PostMapping("/authenticate")
    public Object authenticateUserDetails(@RequestBody JwtRequest jwtRequest) throws Exception {

        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        final UserDetails userDetails = portalAuthentification
                .loadUserByUsername(jwtRequest.getUsername());

        SystemUsers systemUsers = portalAuthentification.getUserDetails(jwtRequest.getUsername());
        HoldUserDetails holdUserDetails = new HoldUserDetails();


    //    authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
        final String token = jwtTokenUtil.generateToken(userDetails);
        holdUserDetails.setAccessToken(token);
        holdUserDetails.setUserDetails(systemUsers);

        return holdUserDetails;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class HoldUserDetails{
        private String accessToken;
        private SystemUsers userDetails;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS found", e);
        }
    }

    // create system user
    @PostMapping("/create/system-user")
    public Object createSystemUser(@RequestBody SystemUsers systemUsers){
        return accessControlService.CREATE_SYSTEM_USER(systemUsers);
    }

    // add role to user
    @PostMapping("/add-roles/{id}")
    public Object addUserRoles(@RequestBody List<Roles> roles, @PathVariable long id){
        return accessControlService.ADD_ROLES_TO_SYSTEM_USER(roles,id);
    }
    // create privilege
    @PostMapping("/create/privilege")
    public Object createPrivilege(@RequestBody Privileges privileges){
        return accessControlService.CREATE_PRIVILEGE(privileges);
    }
    @PostMapping("/view/privileges")
    public Object viewPrivileges(){
        return accessControlService.VIEW_PRIVILEGES();
    }
    @PostMapping("/view/roles")
    public Object viewRoles(){
        return accessControlService.VIEW_ROLES();
    }
    @PostMapping("/view/system-users")
    public Object viewSystemUsers(@RequestBody StatementGetWrapper wrapper){

        Pageable pageable = PageRequest.of(wrapper.getPage(), wrapper.getSize());

        Page<SystemUsers> systemUsersPage = systemUserRepository.findAllBySoftDelete(false,pageable);
      //  return accessControlService.VIEW_SYSTEM_USERS(page,size);
        return systemUsersPage;
    }

    // add privilege to role
    @PostMapping("/add-privilege/{id}")
    public Object addPrivilegeToRole(@RequestBody List<Privileges> privileges,@PathVariable long id){
        return accessControlService.ADD_PRIVILEGE_TO_ROLE(privileges,id);
    }
    // create role
    @PostMapping("/create/role")
    public Object createRole(@RequestBody Roles roles){
        return  accessControlService.CREATE_ROLE(roles);
    }


}
