package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.ContributionSources;

import java.util.List;

/**
 * contains contribution sources ,
 */
public interface DataRequirementsService {

    ContributionSources addCotnributionSource(ContributionSources contributionSources);
    List<ContributionSources> getCotnributionSource();
}
