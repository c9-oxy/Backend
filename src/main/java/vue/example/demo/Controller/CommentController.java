package vue.example.demo.Controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vue.example.demo.Mapper.Commentmapper;
import vue.example.demo.Mapper.Postmapper;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class CommentController {

    @Autowired
    private Postmapper postmapper;

    @Autowired
    private Commentmapper commentmapper;

    @GetMapping("/comments/{postNo}")
    public List<HashMap> commList(@PathVariable String postNo) { //게시판 id와 일치하는 post들을 반환하는 함수
        try {
            List<HashMap> result = commentmapper.getComments(postNo); //이하 board search와 같음

            if (result == null) {
                return null;
            } else {
                return result;
            }
        } catch (Exception e) {
            System.out.println("검색 실패: " + e.getMessage());
            return null;
        }
    }

    @PostMapping("/comments") //댓글 입력 관리
    public void registerUser(@RequestBody HashMap<String, String> newComm) {
        try {
            
            System.out.println("전송받은 값: " + newComm);
            if (newComm.get("commClass").equals("0")) { //만약 댓글 클래스가 0, 부모 댓글일 경우(참고로 받아와지는게 문자열임)
                newComm.put("commGroup", "GROUP_SEQ.NEXTVAL");
                commentmapper.insertComment(newComm); //바로 데이터 전송
            } else { //부모 댓글이 아닐 경우, 즉 자식 댓글일 경우
                System.out.println("댓글 클래스: " + newComm.get("commClass"));
                newComm.put("commGroup", newComm.get("commGroup"));
                System.out.println("자식 값: " + newComm);
                commentmapper.insertReply(newComm); //바로 데이터 전송

            }
            // 새 post 정보를 DB에 삽입
            // commentmapper.insertComment(newComm);
        } catch (Exception e) {
            System.out.println("글 작성 실패: " + e.getMessage());
            // 예외 발생하면 실패 메시지를 반환
        }
    }

}
