package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.portal.Privileges;
import ekenya.co.ke.tontines.dao.entitites.portal.Roles;
import ekenya.co.ke.tontines.dao.entitites.portal.SystemUsers;
import ekenya.co.ke.tontines.dao.repositories.portal.RolesRepository;
import ekenya.co.ke.tontines.dao.repositories.portal.SystemUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortalAuthentification implements UserDetailsService {

    @Autowired
    private SystemUserRepository systemUserRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<SystemUsers> su = systemUserRepository.findAllByEmail(s);


        List<Privileges> privilegesList = new ArrayList<>();
        if (su.size() > 0){
            for(SystemUsers systemUsers : su) {

                System.out.println("total roles..."+systemUsers.getRoles().size());
                for (Roles r : systemUsers.getRoles()){
                   authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
                    Collection<GrantedAuthority> pr = new ArrayList<>();

                    System.out.println("total privileges..."+ r.getPrivileges());
                    for (Privileges p : r.getPrivileges()){
                        privilegesList.add(p);
                       pr.add(new SimpleGrantedAuthority(p.getName()));
                   }

                }

                authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                        "ROLE_TRUSTED_CLIENTS");
            }

            if (privilegesList.size() > 0){
                return new User(s,su.get(0).getPassword(),getAuthorities(privilegesList));
            }else{
                return new User(s,su.get(0).getPassword(),authorities);
            }


        }else{
            throw new UsernameNotFoundException("invalid email");
        }

    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Privileges> privileges) {
        List<GrantedAuthority> authorities
                = new ArrayList<>();
        for (Privileges p: privileges) {
            authorities.add(new SimpleGrantedAuthority(p.getName()));
        }
        return authorities;
    }

    public SystemUsers getUserDetails(String s){
        List<SystemUsers> su = systemUserRepository.findAllByEmail(s);
        return su.get(0);
    }
}
