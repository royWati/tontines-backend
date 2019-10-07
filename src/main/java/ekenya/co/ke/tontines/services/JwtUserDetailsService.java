package ekenya.co.ke.tontines.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ekenya.co.ke.tontines.dao.entitites.Members;
import ekenya.co.ke.tontines.dao.repositories.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private MembersRepository membersRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities;
        for (Members  users : membersRepository.findAllByPhoneNumber(s)) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                    "ROLE_TRUSTED_CLIENTS");


            String user_details = null;
            try {
                user_details = new ObjectMapper().writeValueAsString(users);
            } catch (IOException e) {
                //TODO log error
            }
            // TODO: Load Userdetails to Cache System for faster processing
            return new User(s, users.getPassword(), authorities);
        }
        // If user not found. Throw this exception.
        throw new UsernameNotFoundException("email not found");
    }
}
