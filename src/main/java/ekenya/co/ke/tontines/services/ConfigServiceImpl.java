package ekenya.co.ke.tontines.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import ekenya.co.ke.tontines.dao.entitites.Department;
import ekenya.co.ke.tontines.dao.entitites.Region;
import ekenya.co.ke.tontines.dao.wrappers.*;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final static Logger logger = Logger.getLogger(ConfigServiceImpl.class.getName());

    @Value("${app-configs.countries-directory}")
    public String countryFile;
    @Value("${app-configs.regions-directory}")
    public String regionsFiles;
    @Value("${app-configs.nationality-directory}")
    public String nationalityFiles;
    @Value("${app-configs.sms-from}")
    public String smsFrom;
    @Value("${app-configs.sms-url}")
    public String smsUrl;

    @Autowired
    private EntityServicesRequirementsV1 entityServicesRequirementsV1;
    @Override
    public List<Countries> GET_COUNTRY_CODES() {



        InputStream is = null;
        try {
            is = new FileInputStream(countryFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Countries> data;
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(new InputStreamReader(Objects.requireNonNull(is), StandardCharsets.UTF_8));

        Gson gson = new Gson();
        data = gson.fromJson(jsonElement, new TypeToken<List<Countries>>() {
        }.getType());

        return data;

    }

    @Override
    public List<Regions> SENEGAL_REGIONS() {

        System.out.println(regionsFiles);
        InputStream is = null;
        try {
            is = new FileInputStream(regionsFiles);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Regions> data;
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(new InputStreamReader(Objects.requireNonNull(is), StandardCharsets.UTF_8));

        Gson gson = new Gson();
        data = gson.fromJson(jsonElement, new TypeToken<List<Regions>>() {
        }.getType());


        try {
            logger.info("senegal region..."+new ObjectMapper().writeValueAsString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public List<String> NATIOANALITIES() {
        InputStream is = null;
        try {
            is = new FileInputStream(nationalityFiles);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> data;
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(new InputStreamReader(Objects.requireNonNull(is), StandardCharsets.UTF_8));

        Gson gson = new Gson();
        data = gson.fromJson(jsonElement, new TypeToken<List<String>>() {
        }.getType());

        return data;
    }

    @Override
    public void ADD_REGIONS(List<Regions> regionsList) {

        if (entityServicesRequirementsV1.findAllRegions().size() == 0){

            List<Region> list = new ArrayList<>();


            for (Regions regions : regionsList) {
                List<Department> departments = new ArrayList<>();

                regions.getDepartments().forEach(departments1 -> {
                    Department d = new Department();
                    d.setName(departments1.getName());
                    Region region = new Region();
                    region.setId(regions.getId());
                   d.setRegion(region);
                    departments.add(d);
                });



                Region region = new Region();
                region.setName(regions.getRegion());
                region.setDepartments(departments);
                list.add(region);
            }

            entityServicesRequirementsV1.addAllRegions(list);
        }

    }

    @Override
    public UniversalResponse GET_COUNTRIES() {
        Response response= new Response(200,"countries retrieved successfully");
        return new UniversalResponse(response,GET_COUNTRY_CODES());
    }

    @Override
    public UniversalResponse GET_SENEGAL_REGIONS() {

        // check if the regions are present in the database before adding the data
        List<Regions> regionsList = SENEGAL_REGIONS();
        ADD_REGIONS(regionsList);

        List<Region> list = entityServicesRequirementsV1.findAllRegions();
        Response response= new Response(200,"senegal regions retrieved successfully");
        return new UniversalResponse(response,list);
    }

    @Override
    public UniversalResponse GET_NATIONALITIES() {
        // check if the regions are present in the database before adding the data
        List<String> nationalities = NATIOANALITIES();

        Response response= new Response(200,"nationalities retrieved successfully");
        return new UniversalResponse(response,nationalities);
    }

    @Override
    public void SEND_SMS_(String phoneNumber, String message) {
        SmsRequestBody smsRequestBody = new SmsRequestBody();
        smsRequestBody.setFrom(smsFrom);
        smsRequestBody.setMessage(message);
        smsRequestBody.setTo(phoneNumber);
        smsRequestBody.setTransactionID("");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<SmsRequestBody> entity = new HttpEntity<>(smsRequestBody);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(smsUrl,entity,String.class);

        logger.info("message response..."+responseEntity.getBody());
    }




}

