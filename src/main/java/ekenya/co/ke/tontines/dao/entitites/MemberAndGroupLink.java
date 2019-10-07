package ekenya.co.ke.tontines.dao.entitites;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "member_and_group_link_tbl")
public class MemberAndGroupLink extends BaseEntity{

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "member_group", nullable = false)
    @JsonProperty("memberGroup")
    private MemberGroups memberGroup;
    @ManyToOne()
    @JoinColumn(name = "member", nullable = false)
    @JsonProperty("member")
    private Members member;
    private boolean hasAcceptedInvite;

}
