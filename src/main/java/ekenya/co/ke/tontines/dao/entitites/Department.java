package ekenya.co.ke.tontines.dao.entitites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments_tbl")
@JsonIgnoreProperties({"region"})
public class Department extends BaseEntity {
    private String name;
    @ManyToOne
    @JoinColumn()
    @JsonIgnore
    @JsonBackReference
    private Region region;

    public Department(String name) {
        this.name = name;
    }
}
