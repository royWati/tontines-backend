package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.ContributionSources;
import ekenya.co.ke.tontines.dao.wrappers.Response;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServicesImpl implements DataServices {

    @Autowired DataRequirementsService dataRequirementsService;
    @Override
    public UniversalResponse ADD_CONTRIBUTION_SOURCES(ContributionSources contributionSources) {
        return new UniversalResponse(new Response(200,"Contribution sources added successfully"),
                dataRequirementsService.addCotnributionSource(contributionSources));
    }
}
