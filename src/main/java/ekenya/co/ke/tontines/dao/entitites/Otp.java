package ekenya.co.ke.tontines.dao.entitites;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "otp_tbl")
public class Otp extends BaseEntity{
    private String otpValue;
    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "member", nullable = false)
    @JsonMerge
    @JsonProperty("member")
    private Members members;
    private String otpType;

    @PrePersist
    public void generateOtp(){
        long number = (long) ((Math.random()) * 750000000);
        otpValue  = String.valueOf(number);

    }
}
