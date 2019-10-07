package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.portal.Privileges;
import ekenya.co.ke.tontines.dao.entitites.portal.Roles;
import ekenya.co.ke.tontines.dao.entitites.portal.SystemUsers;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;

import java.util.List;

public interface AccessControlService {

    UniversalResponse CREATE_PRIVILEGE(Privileges privileges);
    UniversalResponse CREATE_ROLE(Roles roles);
    UniversalResponse ADD_PRIVILEGE_TO_ROLE(List<Privileges> privilegesList, long roleId);
    UniversalResponse CREATE_SYSTEM_USER(SystemUsers systemUsers);
    UniversalResponse ADD_ROLES_TO_SYSTEM_USER(List<Roles> rolesList,long id);
}
