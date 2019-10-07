package ekenya.co.ke.tontines.services;

import ekenya.co.ke.tontines.dao.entitites.Region;
import ekenya.co.ke.tontines.dao.wrappers.Countries;
import ekenya.co.ke.tontines.dao.wrappers.Regions;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ConfigService {
    List<Countries> GET_COUNTRY_CODES();
    List<Regions> SENEGAL_REGIONS();
    List<String> NATIOANALITIES();
    void ADD_REGIONS(List<Regions> regionsList);

    UniversalResponse GET_COUNTRIES();
    UniversalResponse GET_SENEGAL_REGIONS();
    UniversalResponse GET_NATIONALITIES();

    void SEND_SMS_(String phoneNumber,String message);


}
