package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.portal.Privileges;
import ekenya.co.ke.tontines.dao.entitites.portal.Roles;
import ekenya.co.ke.tontines.dao.entitites.portal.SystemUsers;
import ekenya.co.ke.tontines.dao.repositories.portal.PrivilegesRepository;
import ekenya.co.ke.tontines.dao.repositories.portal.RolesRepository;
import ekenya.co.ke.tontines.dao.repositories.portal.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AccessControlRequirementServiceImpl implements AccessControlRequirementService {


    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PrivilegesRepository privilegesRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;


    @Override
    public Privileges createPrivieleges(Privileges privileges) {
        return privilegesRepository.save(privileges);
    }

    @Override
    public Roles createRole(Roles roles) {
        return rolesRepository.save(roles);
    }

    @Override
    public Roles addPrivilegesToRole(List<Privileges> privilegesList, long roleId) {

        Roles roles = rolesRepository.findById(roleId).get();

        Collection<Privileges> privileges = roles.getPrivileges();
        privileges.addAll(privilegesList);

        roles.setPrivileges(privileges);

        return rolesRepository.save(roles);

    }

    @Override
    public SystemUsers createSystemUser(SystemUsers systemUsers) {
        return systemUserRepository.save(systemUsers);
    }

    @Override
    public SystemUsers addRolesToSystemUser(List<Roles> rolesList,long id) {

        SystemUsers su = systemUserRepository.findById(id).get();

        Collection<Roles> rolesCollection = su.getRoles();
        rolesCollection.addAll(rolesList);

        su.setRoles(rolesCollection);

        return systemUserRepository.save(su);
    }
}
