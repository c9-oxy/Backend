package vue.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentDTO {

    private String BOARD_ID;
    private String BOARD_NAME;
    private int BOARD_COUNT;

}
