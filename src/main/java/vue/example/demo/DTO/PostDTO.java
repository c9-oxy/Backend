package vue.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostDTO {

    private int POST_NO;
    private String BOARD_ID;
    private String POST_TITLE;
    private String POST_CONTENT;
    private String POST_AUTHOR;
    private String POST_TIME;
    private String POST_EDITS;
    private String POST_EDIT_TIME;

}
