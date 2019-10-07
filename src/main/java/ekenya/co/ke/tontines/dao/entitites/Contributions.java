package ekenya.co.ke.tontines.dao.entitites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contributions_tbl")
public class Contributions  extends BaseEntity{

    private String name;
    private String targetAmount;
    private String amountPerMember;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean penalityAvailable;
    private boolean remainderSet;
    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "member_groups", nullable = false)
    @JsonProperty("memberGroups")
    private MemberGroups memberGroup;
    @ManyToOne()
    @JoinColumn(name = "contribution_type", nullable = false)
    @JsonProperty("contributionType")
    private ContributionType contributionType;
    @ManyToOne()
    @JoinColumn(name = "indivualContribution_types", nullable = false)
    @JsonProperty("indivualContributionType")
    private IndivualContributionTypes indivualContributionType;
    @ManyToOne()
    @JoinColumn(name = "schedule_type", nullable = false)
    @JsonProperty("scheduleType")
    private ScheduleTypes scheduleType;
}
