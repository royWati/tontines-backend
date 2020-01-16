package ekenya.co.ke.tontines.controller.backend;

import ekenya.co.ke.tontines.dao.entitites.ContributionSources;
import ekenya.co.ke.tontines.dao.wrappers.StatementGetWrapper;
import ekenya.co.ke.tontines.services.EntityManagementService;
import ekenya.co.ke.tontines.services.EntityManagementServiceV2;
import ekenya.co.ke.tontines.services.portal.DataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portal/data")
public class BackEndController {

    @Autowired DataServices dataServices;

    @Autowired
    private EntityManagementService entityManagementService;
    @Autowired
    EntityManagementServiceV2 entityManagementServiceV2;

    @PostMapping("/add-contribution-source")
    public Object addContributionSource(@RequestBody ContributionSources contributionSources){
        return dataServices.ADD_CONTRIBUTION_SOURCES(contributionSources);
    }
    @GetMapping("/get-contribution-source")
    public Object getContributionSource(){
        return dataServices.GET_CONTRIBUTION_SOURCES();
    }

    @GetMapping("/view-groups")
    public Object viewAllGroups(@RequestParam int page, @RequestParam int size){
        return entityManagementService.GET_ALL_MEMBER_GROUPS(page, size);
    }
    @GetMapping("view-group-members")
    public Object viewGroupMembers(@RequestBody StatementGetWrapper wrapper){
        return  entityManagementService.GET_ALL_MEMBERS_IN_GROUP(wrapper.getId(), wrapper.getPage(),
                wrapper.getSize());
    }
    @GetMapping("/view-contributions-in-group")
    public Object viewContributionsInGroup(@RequestBody StatementGetWrapper wrapper){
        return entityManagementService.GET_GROUP_CONTRIBUTIONS(wrapper.getId(), wrapper.getPage(),
                wrapper.getSize());
    }
    @GetMapping("/view-contribution-statement")
    public Object viewContributionContributionStatements(@RequestBody StatementGetWrapper wrapper){
        return entityManagementServiceV2.GET_GROUP_STATEMENTS(wrapper);
    }
}
