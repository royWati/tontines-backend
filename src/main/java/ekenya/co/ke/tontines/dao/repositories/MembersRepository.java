package ekenya.co.ke.tontines.dao.repositories;

import ekenya.co.ke.tontines.dao.entitites.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.reflect.Member;
import java.util.List;

public interface MembersRepository extends JpaRepository<Members,Long> {

    String strSearchPhone = "SELECT m FROM Members m where m.phoneNumber LIKE %:phone% and m.softDelete = false";

    List<Members> findAllByEmail(String email);
    List<Members> findAllByPhoneNumber(String phonenumber);
    List<Members> findAllById(long id);
    List<Members> findAllByIdentification(String identification);

    @Query(strSearchPhone)
    List<Members> searchPhoneNumber(@Param("phone")String phoneNumber);
}
