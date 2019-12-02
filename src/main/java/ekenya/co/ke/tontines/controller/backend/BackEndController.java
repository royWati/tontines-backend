package ekenya.co.ke.tontines.controller.backend;

import ekenya.co.ke.tontines.dao.entitites.ContributionSources;
import ekenya.co.ke.tontines.services.portal.DataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal/data")
public class BackEndController {

    @Autowired DataServices dataServices;

    @PostMapping("/add-contribution-source")
    public Object addContributionSource(@RequestBody ContributionSources contributionSources){
        return dataServices.ADD_CONTRIBUTION_SOURCES(contributionSources);
    }
}
