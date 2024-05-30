package vue.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {

    private String USER_NO;
    private String USER_ID;
    private String USER_PW;
    private String USER_NAME;
    private String USER_RANK;
    private String USER_ACTIVATION;
    private int USER_COUNT;
}
