package ekenya.co.ke.tontines.dao.repositories.portal;

import ekenya.co.ke.tontines.dao.entitites.portal.SystemUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemUserRepository extends JpaRepository<SystemUsers,Long> {

    List<SystemUsers> findAllByEmail(String email);

    Page<SystemUsers> findAllBySoftDelete(boolean status, Pageable pageable);
}
