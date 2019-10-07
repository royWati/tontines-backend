package ekenya.co.ke.tontines.dao.repositories;

import ekenya.co.ke.tontines.dao.entitites.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Otp,Long> {
}
