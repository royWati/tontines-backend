package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.portal.Privileges;
import ekenya.co.ke.tontines.dao.entitites.portal.Roles;
import ekenya.co.ke.tontines.dao.entitites.portal.SystemUsers;
import ekenya.co.ke.tontines.dao.wrappers.Response;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessControlServiceImpl implements AccessControlService {

    @Autowired
    private AccessControlRequirementService accessControlRequirementService;
    @Override
    public UniversalResponse CREATE_PRIVILEGE(Privileges privileges) {

        return new UniversalResponse(new Response(200,"privilege created successfully"),
                accessControlRequirementService.createPrivieleges(privileges));
    }

    @Override
    public UniversalResponse CREATE_ROLE(Roles roles) {
        return new UniversalResponse(new Response(200,"role created successfully"),
                accessControlRequirementService.createRole(roles));
    }

    @Override
    public UniversalResponse ADD_PRIVILEGE_TO_ROLE(List<Privileges> privilegesList, long roleId) {
        return new UniversalResponse(new Response(200,"privileges added successfully"),
                accessControlRequirementService.addPrivilegesToRole(privilegesList, roleId));
    }

    @Override
    public UniversalResponse CREATE_SYSTEM_USER(SystemUsers systemUsers) {
        return new UniversalResponse(new Response(200,"system user created successfully"),
                accessControlRequirementService.createSystemUser(systemUsers));
    }

    @Override
    public UniversalResponse ADD_ROLES_TO_SYSTEM_USER(List<Roles> rolesList, long id) {
        return new UniversalResponse(new Response(200,"privileges added successfully"),
                accessControlRequirementService.addRolesToSystemUser(rolesList, id));
    }

    @Override
    public UniversalResponse VIEW_PRIVILEGES() {
        return new UniversalResponse(new Response(200,"all privileges retrived successfully"),
                accessControlRequirementService.getPrivileges());
    }

    @Override
    public UniversalResponse VIEW_ROLES() {
        return new UniversalResponse(new Response(200,"all privileges retrived successfully"),
                accessControlRequirementService.getAllRoles());
    }

    @Override
    public UniversalResponse VIEW_SYSTEM_USERS(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return new UniversalResponse(new Response(200,"system users retrieved successfully"),
                accessControlRequirementService.getSystemUsers(pageable));
    }
}
