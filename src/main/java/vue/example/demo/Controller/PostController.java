package vue.example.demo.Controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/posts/{boardId}")
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

    @GetMapping("/posts/{boardId}/{postNo}") //글의 내용을 보는 코드입니다. 먼저 게시판id와 글 번호를 가져옵니다.
    public HashMap getMethodName(@PathVariable String boardId, @PathVariable String postNo) { //PathVariable을 두 개 사용해서 두 개의 매개변수를 선언합니다.
        try {
            HashMap board = boardmapper.getBoardId(boardId); //boardId 매개변수를 통해 boardmapper에서 boardId를 가져옵니다.
            String id = (String) board.get("BOARD_ID"); //boardId를 id 변수에 적용합니다.
            HashMap<String, String> post = new HashMap<String, String>(); //그리고 post라는 해시맵을 선언합니다.
            post.put("boardId", id); //해시맵 내에 id와 postNo를 넣습니다.
            post.put("postNo", postNo);

            int isPostTag = postmapper.checkPostTag(postNo);
            HashMap result = postmapper.getPostContent(post); //이것을 postmapper의 getPostContent에 보내 post의 제목, 내용 등을 불러옵니다.

            if (isPostTag > 0) {
                List<HashMap> getPostTags = postmapper.getPostTag(postNo);
                result.put("POST_TAG", getPostTags);
            }
            System.out.println(result);

            return result; //이를 리턴합니다.
        } catch (Exception e) {

            System.out.println("콘텐츠 검색 실패: " + e.getMessage());
            return null;
        }
    }

    @PostMapping("/posts") //글 작성 함수
    public void registerUser(@RequestBody HashMap newPost) {
        try {
            // 새 post 정보를 DB에 삽입
            postmapper.insertPost(newPost);
            List<String> tags = (List<String>) newPost.get("postTags");

            if (tags.size() != 0) { //태그 유무 체크
                String postNo = Integer.toString((int) newPost.get("POST_SEQ")); //추가하는 post의 번호 추출
                HashMap<String, String> tagMatch = new HashMap<String, String>(); //그리고 post라는 해시맵을 선언합니다.

                for (int i = 0; i < tags.size(); i++) { //반복문을 통해 게시물에 태그를 적용
                    String tagName = tags.get(i); //태그네임 선언
                    int isTag = Integer.parseInt(String.valueOf(postmapper.checkTag(tagName))); //해당 태그가 이미 존재하는지 검색

                    if (isTag > 0) { //해당 태그가 존재한다면
                        tagMatch.put("postNo", postNo);
                        tagMatch.put("tagName", tags.get(i));
                        postmapper.insertMatch(tagMatch); //태그 매치 테이블에 포스트 번호와 태그네임 삽입

                    } else { //해당 태그가 존재하지 않는다면
                        postmapper.insertTag(tagName); //태그 테이블에 생성된 태그 삽입
                        tagMatch.put("postNo", postNo); //이후 동일
                        tagMatch.put("tagName", tags.get(i));
                        postmapper.insertMatch(tagMatch);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("글 작성 실패: " + e.getMessage());
            // 예외 발생하면 실패 메시지를 반환
        }
    }

    @PutMapping("/posts") //포스트 업데이트
    public void updatePost(@RequestBody HashMap updatePost) {
        try {
            // 업데이트할 post 정보를 DB에 반영
            postmapper.updatePost(updatePost);
            List<String> tags = (List<String>) updatePost.get("postTags");

            if (tags.size() != 0) { //태그 유무 체크
                String postNo = Integer.toString((int) updatePost.get("POST_NO")); //추가하는 post의 번호 추출
                System.out.println("글번호:" + postNo);
                HashMap<String, String> tagMatch = new HashMap<String, String>(); //그리고 post라는 해시맵을 선언합니다.

                for (int i = 0; i < tags.size(); i++) { //반복문을 통해 게시물에 태그를 적용
                    String tagName = tags.get(i); //태그네임 선언
                    int isTag = Integer.parseInt(String.valueOf(postmapper.checkTag(tagName))); //해당 태그가 이미 존재하는지 검색

                    if (isTag > 0) { //해당 태그가 존재한다면
                        tagMatch.put("postNo", postNo);
                        tagMatch.put("tagName", tags.get(i));
                        postmapper.insertMatch(tagMatch); //태그 매치 테이블에 포스트 번호와 태그네임 삽입

                    } else { //해당 태그가 존재하지 않는다면
                        postmapper.insertTag(tagName); //태그 테이블에 생성된 태그 삽입
                        tagMatch.put("postNo", postNo); //이후 동일
                        tagMatch.put("tagName", tags.get(i));
                        postmapper.insertMatch(tagMatch);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("글 업데이트 실패: " + e.getMessage());
            // 예외 발생 시 실패 메시지 반환
        }
    }

    @DeleteMapping("/posts/{postNo}") // POST_NO 번호를 가져와 삭제
    public void deletePost(@PathVariable int postNo) {
        try {
            // postNo에 해당하는 게시글을 DB에서 삭제
            postmapper.deletePost(postNo);
        } catch (Exception e) {
            System.out.println("글 삭제 실패: " + e.getMessage());
            // 예외 발생 시 실패 메시지 반환
        }
    }

    @GetMapping("/tags/{tagName}")
    public List<HashMap> WooJoon(@PathVariable String tagName) {
        try {
            List<HashMap> result = postmapper.searchTag(tagName); // '황우준' 태그를 기반으로 게시글을 검색
            if (result == null || result.isEmpty()) { // 만약 일치하는 게시글이 없을 시 null 반환
                return null;
            } else {
                // 1개 이상일 시 태그 이름과 일치하는 게시글을 list 형태로 반환
                return result; // 값 반환
            }
        } catch (Exception e) {
            System.out.println("검색 실패: " + e.getMessage());
            return null;
        }
    }

}
