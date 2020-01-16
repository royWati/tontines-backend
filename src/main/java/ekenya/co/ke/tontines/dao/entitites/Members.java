package ekenya.co.ke.tontines.dao.entitites;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members_tbl")
public class Members extends BaseEntity {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String countyCode;
    private String identification;
    private String Nationality;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    //  @JsonSerialize(as = Region.class)
    @JoinColumn(name = "department", nullable = false)
    @JsonProperty("department")
    private Department department;
    private String password;
    private boolean pendingRegistrationStatus;
    private boolean isRegisteredMember;

    private String memberGroups;
    private String email;

    @PrePersist
    public void createData(){
        pendingRegistrationStatus = true;
    }
}
