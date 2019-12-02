package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.ContributionSources;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;

public interface DataServices {

    UniversalResponse ADD_CONTRIBUTION_SOURCES(ContributionSources contributionSources);
}
