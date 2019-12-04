package ekenya.co.ke.tontines.dao.repositories;

import ekenya.co.ke.tontines.dao.entitites.ContributionSources;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionSourceRepository extends JpaRepository<ContributionSources,Long> {

    Page<ContributionSources> findAllBySoftDelete(boolean status, Pageable pageable);
}
