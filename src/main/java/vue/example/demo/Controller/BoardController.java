package vue.example.demo.Controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import vue.example.demo.Mapper.Boardmapper;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class BoardController {

    @Autowired
    private Boardmapper boardmapper;

    @GetMapping("/board/{searchWord}")
    public List<HashMap> searchBoard(@PathVariable String searchWord) { //검색어와 일치하는 게시판을 반환하는 함수
        try {
            List<HashMap> result = boardmapper.getBoards(searchWord); //먼저 검색어와 일치하는 게시판을 검색
            if (result == null) { //만약 일치하는 게시판이 없을 시 null 반환
                return null;
            } else {
                //1개 이상일 시 검색어와 일치하는 게시판을 list 형태로 반환
                return result; //값 반환
            }
        } catch (Exception e) {
            System.out.println("검색 실패: " + e.getMessage());
            return null;
        }
    }

    @GetMapping("/board/name/{searchWord}")
    public HashMap boardName(@PathVariable String searchWord) { //검색어와 일치하는 게시판을 반환하는 함수
        try {
            HashMap result = boardmapper.getBoardId(searchWord); //먼저 검색어와 일치하는 게시판을 검색
            if (result == null) { //만약 일치하는 게시판이 없을 시 null 반환
                return null;
            } else {
                //1개 이상일 시 검색어와 일치하는 게시판을 list 형태로 반환
                return result; //값 반환
            }
        } catch (Exception e) {
            System.out.println("검색 실패: " + e.getMessage());
            return null;
        }
    }

    @GetMapping("/board") //게시판 순위를 매깁니다.
    public List<HashMap> rankedBoard() {
        try {
            List<HashMap> result = boardmapper.getBoardCount(); //이하 board search와 같음
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

}
