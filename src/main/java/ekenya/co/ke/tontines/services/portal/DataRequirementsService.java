package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.ContributionSources;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * contains contribution sources ,
 */
public interface DataRequirementsService {

    ContributionSources addCotnributionSource(ContributionSources contributionSources);
    List<ContributionSources> getCotnributionSource();
}
