package ekenya.co.ke.tontines.dao.repositories;

import ekenya.co.ke.tontines.dao.entitites.MemberAndGroupLink;
import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import ekenya.co.ke.tontines.dao.entitites.Members;
import ekenya.co.ke.tontines.dao.repositories.jpql.ViewMemberGroups;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.reflect.Member;
import java.util.List;

public interface MemberAndGroupLinkRepository extends JpaRepository<MemberAndGroupLink ,Long> {

    String strLoadMemberGroupsForMember =
            "SELECT NEW ekenya.co.ke.tontines.dao.repositories.jpql.ViewMemberGroups(" +
                    "mgl.memberGroup," +
                    "mgl.hasAcceptedInvite" +
                    ") FROM MemberAndGroupLink mgl WHERE mgl.member = :id and mgl.softDelete = false ";

    String strLoadMemberGroupsForMemberInvites =
            "SELECT NEW ekenya.co.ke.tontines.dao.repositories.jpql.ViewMemberGroups(" +
                    "mgl.memberGroup," +
                    "mgl.hasAcceptedInvite" +
                    ") FROM MemberAndGroupLink mgl WHERE mgl.member = :id and mgl.softDelete = false " +
                    "and mgl.hasAcceptedInvite = false ";

    Page<MemberAndGroupLink> findAllByMemberGroupAndSoftDelete(MemberGroups memberGroups, Pageable pageable,
                                                               boolean state);
    List<MemberAndGroupLink> findAllByMemberGroupAndMemberAndSoftDelete(MemberGroups memberGroups,
                                                                        Members members,boolean state);

    @Query(strLoadMemberGroupsForMember)
    Page<ViewMemberGroups> loadMemberGroupsForMember(@Param("id") Members id,Pageable pageable);
    @Query(strLoadMemberGroupsForMemberInvites)
    Page<ViewMemberGroups> loadMemberGroupsForMemberInvites(@Param("id") Members id,Pageable pageable);

}
