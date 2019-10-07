package ekenya.co.ke.tontines.dao.repositories;

import ekenya.co.ke.tontines.dao.entitites.MemberAndGroupLink;
import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import ekenya.co.ke.tontines.dao.entitites.Members;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.List;

public interface MemberAndGroupLinkRepository extends JpaRepository<MemberAndGroupLink ,Long> {

    Page<MemberAndGroupLink> findAllByMemberGroupAndSoftDelete(MemberGroups memberGroups, Pageable pageable,
                                                               boolean state);
    List<MemberAndGroupLink> findAllByMemberGroupAndMemberAndSoftDelete(MemberGroups memberGroups,
                                                                        Members members,boolean state);
}
