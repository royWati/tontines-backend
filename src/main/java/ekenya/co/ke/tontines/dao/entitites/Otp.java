package ekenya.co.ke.tontines.dao.entitites;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.Random;

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
        Random r = new Random();
        int low = 1000;
        int high = 9999;
        int result = r.nextInt(high-low) + low;
        otpValue  = String.valueOf(result);

    }
}
