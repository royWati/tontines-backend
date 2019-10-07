package ekenya.co.ke.tontines.dao.entitites;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "regions_tbl")
@EqualsAndHashCode(exclude = "departments")
public class Region extends BaseEntity{
    private String name;

    @OneToMany(mappedBy = "region",cascade = CascadeType.ALL)
    public List<Department> departments;

    public Region (String name,Department... departments){
        this.name = name;
        this.departments = Stream.of(departments).collect(Collectors.toList());
        this.departments.forEach(department -> department.setRegion(this));
    }


}
