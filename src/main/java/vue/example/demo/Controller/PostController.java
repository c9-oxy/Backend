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

import vue.example.demo.Mapper.Boardmapper;
import vue.example.demo.Mapper.Postmapper;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class PostController {

    @Autowired
    private Postmapper postmapper;

    @Autowired
    private Boardmapper boardmapper;

    @GetMapping("/board/post/{boardId}")
    public List<HashMap> postList(@PathVariable String boardId) { //게시판 id와 일치하는 post들을 반환하는 함수
        try {
            List<HashMap> result = postmapper.getPostList(boardId); //이하 board search와 같음
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

    @GetMapping("/board/view/{boardId}/{postNo}") //먼저 게시판id와 글 번호를 가져옵니다.
    public HashMap getMethodName(@PathVariable String boardId, @PathVariable String postNo) { //PathVariable을 두 개 사용해서 두 개의 매개변수를 선언합니다.
        try {
            HashMap board = boardmapper.getBoardId(boardId); //boardId 매개변수를 통해 boardmapper에서 boardId를 가져옵니다.
            String id = (String) board.get("BOARD_ID"); //boardId를 id 변수에 적용합니다.

            HashMap<String, String> post = new HashMap<String, String>(); //그리고 post라는 해시맵을 선언합니다.
            post.put("boardId", id); //해시맵 내에 id와 postNo를 넣습니다.
            post.put("postNo", postNo);

            HashMap result = postmapper.getPostContent(post); //이것을 postmapper의 getPostContent에 보내 post의 제목, 내용 등을 불러옵니다.
            return result; //이를 리턴합니다.
        } catch (Exception e) {

            System.out.println("콘텐츠 검색 실패: " + e.getMessage());
            return null;
        }
    }

    @PostMapping("/board/write")
    public void registerUser(@RequestBody HashMap<String, String> newPost) {
        try {
            // 새 post 정보를 DB에 삽입
            postmapper.insertPost(newPost);
        } catch (Exception e) {
            System.out.println("글 작성 실패: " + e.getMessage());
            // 예외 발생하면 실패 메시지를 반환
        }
    }

}
