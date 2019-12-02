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
    private int id;
    private int page;
    private int size;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int accountTypeId;
}
