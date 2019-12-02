package ekenya.co.ke.tontines.dao.entitites;

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
@Table(name = "document_library_tbl")
public class DocumentsLibrary extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "group_id",nullable = false)
    @JsonProperty("member")
    private MemberGroups memberGroups;
    private String documentName;
    private String documentFolder;
    private String downloadUrl;
}
