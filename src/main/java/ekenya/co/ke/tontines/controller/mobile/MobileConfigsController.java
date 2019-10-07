package ekenya.co.ke.tontines.controller.mobile;

import ekenya.co.ke.tontines.services.AutoWireInjector;
import ekenya.co.ke.tontines.services.ConfigService;
import lombok.Getter;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/mobile/config")
public class MobileConfigsController {

    @Autowired
    private ConfigService configService;

    @PostMapping("/get-countries")
    public Object getCountries(){
        return  configService.GET_COUNTRIES();
    }
    @PostMapping("/get-senegal-regions")
    public Object getSenegalRegions(){
        return  configService.GET_SENEGAL_REGIONS();
    }
    @PostMapping("/get-nationalites")
    public Object getNationalities(){
        return  configService.GET_NATIONALITIES();
    }
    @PostMapping("/get-schedules")
    public Object getScheduleTypes(){
        return  new ModelAndView("redirect:/portal/config/schedule-contribution/types");
    }
    @PostMapping("/get-group-types")
    public Object getGroupTypes(){
        return  new ModelAndView("redirect:/portal/config/group/types");
    }
    @PostMapping("/get-contribution-types")
    public Object getContributionTypes(){
        return  new ModelAndView("redirect:/portal/config/contribution/types");
    }
    @PostMapping("/get-individual-contribution-types")
    public Object getIndivualContributionTypes(){
        return  new ModelAndView("redirect:/portal/config/indiviual-contribution/types");
    }
}
