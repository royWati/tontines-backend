package ekenya.co.ke.tontines.services.portal;

import ekenya.co.ke.tontines.dao.entitites.ContributionSources;
import ekenya.co.ke.tontines.dao.repositories.ContributionSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataRequirementsServiceImpl implements DataRequirementsService {
    @Autowired  ContributionSourceRepository contributionSourceRepository;
    @Override
    public ContributionSources addCotnributionSource(ContributionSources contributionSources) {
        return contributionSourceRepository.save(contributionSources);
    }

    @Override
    public List<ContributionSources> getCotnributionSource() {
        return contributionSourceRepository.findAll();
    }
}
