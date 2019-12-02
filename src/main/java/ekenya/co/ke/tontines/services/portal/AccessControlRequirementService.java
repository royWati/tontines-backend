package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.portal.Privileges;
import ekenya.co.ke.tontines.dao.entitites.portal.Roles;
import ekenya.co.ke.tontines.dao.entitites.portal.SystemUsers;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.relation.Role;
import java.util.List;

public interface AccessControlRequirementService {

    Privileges createPrivieleges(Privileges privileges);
    Roles createRole(Roles roles);

    List<Privileges> getPrivileges();
    List<Roles> getAllRoles();

    Roles addPrivilegesToRole(List<Privileges> privilegesList,long roleId);

    SystemUsers createSystemUser(SystemUsers systemUsers);

    SystemUsers addRolesToSystemUser(List<Roles> rolesList,long id);

    Page<SystemUsers> getSystemUsers(Pageable pageable);
}
