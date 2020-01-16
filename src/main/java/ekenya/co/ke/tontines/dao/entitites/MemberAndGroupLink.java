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
    @JoinColumn(name = "member_id", nullable = false)
    @JsonProperty("member")
    private Members member;
    private boolean hasAcceptedInvite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_role",nullable = false)
    @JsonProperty("memberRole")
    private MemberRoles memberRole;

    private boolean declinedInvite;


    public MemberAndGroupLink(MemberGroups memberGroup, boolean hasAcceptedInvite) {
        this.memberGroup = memberGroup;
        this.hasAcceptedInvite = hasAcceptedInvite;
    }

    @PrePersist
    public void addData(){
        MemberRoles roles = new MemberRoles();
        roles.setId(3);
        this.memberRole = roles;
    }
}
