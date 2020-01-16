package ekenya.co.ke.tontines.dao.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatementGetWrapper {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int size;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int accountTypeId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int contributionId; // used during data retrival for a member and a contributionId
}
