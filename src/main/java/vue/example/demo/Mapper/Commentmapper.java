package vue.example.demo.Mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Commentmapper { //인터페이스에서 각 쿼리들을 호출이 가능합니다.

    List<HashMap> getComments(String postNo);

    void insertComment(HashMap<String, String> newPost);

}
