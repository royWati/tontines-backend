package ekenya.co.ke.tontines.dao.wrappers;

import ekenya.co.ke.tontines.dao.entitites.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NegativeOrZero;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Regions {
    private int id;
    private String region;
    private List<Departments> departments;
}
