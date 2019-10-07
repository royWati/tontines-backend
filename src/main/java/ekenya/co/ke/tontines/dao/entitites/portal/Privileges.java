package ekenya.co.ke.tontines.dao.entitites.portal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ekenya.co.ke.tontines.dao.entitites.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "preveleges_tbl")
public class Privileges extends BaseEntity {
    private String name;
    @JsonBackReference
    @ManyToMany(mappedBy = "privileges")
    private Collection<Roles> roles;

}
