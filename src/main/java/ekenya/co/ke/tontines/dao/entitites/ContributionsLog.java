package ekenya.co.ke.tontines.dao.entitites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contributions_log_tbl")
public class ContributionsLog extends BaseEntity{

    @ManyToOne()
    @JoinColumn(name = "member_id", nullable = false)
    @JsonMerge
    @JsonProperty("member")
    private Members member;
    @ManyToOne()
    @JoinColumn(name = "contribution", nullable = false)
    @JsonMerge
    @JsonProperty("contribution_id")
    @JsonBackReference
    private Contributions contribution;
    private long amount;
    @ManyToOne()
    @JoinColumn(name = "contribution_source", nullable = false)
    @JsonMerge
    @JsonProperty("contributionSource")
    private ContributionSources contributionSources;
}
