package ekenya.co.ke.tontines.dao.repositories.appconfigs;

import ekenya.co.ke.tontines.dao.entitites.IndivualContributionTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndivualContributionsRepository extends JpaRepository<IndivualContributionTypes,Long> {
}
