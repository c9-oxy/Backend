package vue.example.demo.Mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Postmapper { //인터페이스에서 각 쿼리들을 호출이 가능합니다.

    List<HashMap> getPostList(String searchWord);

    HashMap getPostContent(HashMap<String, String> newPost);

    int insertPost(HashMap<String, String> newPost);

    void updatePost(HashMap<String, String> updatePost);

    void deletePost(int deletePost);

}
