package ekenya.co.ke.tontines.dao.entitites.portal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ekenya.co.ke.tontines.dao.entitites.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles_tbl")
public class Roles extends BaseEntity {
    private String roleName;
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Collection<SystemUsers> systemUsers;


    @ManyToMany()
    @JoinTable(
            name = "role_preveleges_tbl",
            joinColumns = @JoinColumn(
                    name = "role_id",referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id",referencedColumnName = "id"
            )
    )
    private Collection<Privileges> privileges;
}
