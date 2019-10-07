package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.portal.Privileges;
import ekenya.co.ke.tontines.dao.entitites.portal.Roles;
import ekenya.co.ke.tontines.dao.entitites.portal.SystemUsers;

import javax.management.relation.Role;
import java.util.List;

public interface AccessControlRequirementService {

    Privileges createPrivieleges(Privileges privileges);
    Roles createRole(Roles roles);

    Roles addPrivilegesToRole(List<Privileges> privilegesList,long roleId);

    SystemUsers createSystemUser(SystemUsers systemUsers);

    SystemUsers addRolesToSystemUser(List<Roles> rolesList,long id);
}
