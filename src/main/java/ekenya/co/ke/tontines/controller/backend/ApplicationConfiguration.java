package ekenya.co.ke.tontines.controller.backend;

import ekenya.co.ke.tontines.dao.entitites.ContributionType;
import ekenya.co.ke.tontines.dao.entitites.GroupTypes;
import ekenya.co.ke.tontines.dao.entitites.IndivualContributionTypes;
import ekenya.co.ke.tontines.dao.entitites.ScheduleTypes;
import ekenya.co.ke.tontines.dao.wrappers.Response;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import ekenya.co.ke.tontines.services.EntityServicesRequirementsV1;
import ekenya.co.ke.tontines.services.EntityServicesRequirementsV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portal/config")
public class ApplicationConfiguration {

    @Autowired
    private EntityServicesRequirementsV2 entityServicesRequirementsV2;

    @Autowired
    private EntityServicesRequirementsV1 requirementsV1;
    @RequestMapping(value = "/group/types",method = {RequestMethod.POST,RequestMethod.PUT})
    public Object createGroupTypes(@RequestBody GroupTypes groupTypes){

        return new UniversalResponse(new Response(200,"group types created successfully"),
                entityServicesRequirementsV2.createGroupTpe(groupTypes));
    }
    @GetMapping("/group/types")
    public Object getGroupTypes(){
        return new UniversalResponse(new Response(200,"group types retrived successfully"),
                entityServicesRequirementsV2.getGroupTypes());
    }

    @RequestMapping(value = "/contribution/types",method = {RequestMethod.POST,RequestMethod.PUT})
    public Object createContributionTypes(@RequestBody ContributionType contributionType){
        return new UniversalResponse(new Response(200,"contribution types created successfully"),
                entityServicesRequirementsV2.createIndivualContributionTypes(contributionType
                ));
    }
    @GetMapping("/contribution/types")
    public Object getContributionTypes(){
        return new UniversalResponse(new Response(200,"contribution types retrived successfully"),
                entityServicesRequirementsV2.getContributionTypes());
    }

    @RequestMapping(value = "/indiviual-contribution/types",method = {RequestMethod.POST,RequestMethod.PUT})
    public Object createIndiviualContributionTypes(@RequestBody IndivualContributionTypes contributionType){
        return new UniversalResponse(new Response(200,"indivual contrib types created successfully"),
                entityServicesRequirementsV2.createIndivualContributionTypes(contributionType));
    }
    @GetMapping("/indiviual-contribution/types")
    public Object getIndiviualContributionTypes(){
        return new UniversalResponse(new Response(200,"indivual contribution types retrived successfully"),
                entityServicesRequirementsV2.getIndiviualContributionTypes());
    }
    @RequestMapping(value = "/schedule-contribution/types",method = {RequestMethod.POST,RequestMethod.PUT})
    public Object createScheduleContributionTypes(@RequestBody ScheduleTypes scheduleTypes){
        return new UniversalResponse(new Response(200,"schedule types created successfully"),
                entityServicesRequirementsV2.createScheduleType(scheduleTypes));
    }
    @GetMapping("/schedule-contribution/types")
    public Object getScheduleContributionTypes(){
        return new UniversalResponse(new Response(200,"schedule types retrived successfully"),
                entityServicesRequirementsV2.getAllScheduleTypes());
    }

    @GetMapping("/search")
    public Object searchPhoneNumber(@RequestParam String phone){
        return requirementsV1.searchPhoneNumber(phone);
    }
}
