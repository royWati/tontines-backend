package ekenya.co.ke.tontines.dao.repositories;

import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberGroupsRepository  extends JpaRepository<MemberGroups,Long> {

    Page<MemberGroups> findAllBySoftDelete(boolean state, Pageable pageable);
    List<MemberGroups> findAllByIdAndSoftDelete(long id,boolean status);
}
