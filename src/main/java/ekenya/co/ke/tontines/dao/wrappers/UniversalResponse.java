package ekenya.co.ke.tontines.dao.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversalResponse {
    private Response response;
    private Object data;

    public UniversalResponse(Response response) {
        this.response = response;
    }
}
