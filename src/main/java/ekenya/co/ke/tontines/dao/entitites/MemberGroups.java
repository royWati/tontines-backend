package ekenya.co.ke.tontines.dao.entitites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "members_group_tbl")
public class MemberGroups extends BaseEntity{

    private String name;
    private String country;
    private String city;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator", nullable = false)
    @JsonProperty("creator")
    private Members creator;

    @JsonIgnore
    private String groupMembers;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_type", nullable = false)
    @JsonProperty("groupType")
    private GroupTypes groupType;

}
