package ekenya.co.ke.tontines.dao.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MobileLoginResponse {
    private Object access_token;
    private Object userDetails;
}
