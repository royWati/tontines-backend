package ekenya.co.ke.tontines.dao.repositories;

import ekenya.co.ke.tontines.dao.entitites.Contributions;
import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionsRepository extends JpaRepository<Contributions,Long> {


    Page<Contributions> findAllByMemberGroupAndSoftDelete(MemberGroups memberGroups,boolean state,Pageable pageable);
    Page<Contributions> findAllBySoftDelete(boolean state, Pageable pageable);
}
