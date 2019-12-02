package ekenya.co.ke.tontines.dao.repositories;

import ekenya.co.ke.tontines.dao.entitites.Members;
import ekenya.co.ke.tontines.dao.entitites.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OtpRepository extends JpaRepository<Otp,Long> {

    List<Otp> findAllByOtpValueAndMembers(String otp, Members members);
}
