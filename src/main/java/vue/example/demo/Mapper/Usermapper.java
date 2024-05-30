package vue.example.demo.Mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Usermapper { //인터페이스에서 각 쿼리들을 호출이 가능합니다.

    int countUser(String UserId);

    HashMap signInUser(HashMap<String, String> newUser);

    void plusCounts(HashMap<String, String> newUser);

    String updateUser(HashMap<String, String> newUser);

    List<HashMap> listUser();

    HashMap getUser(String userId);

    void resetFailCount(String UserId);

    void notuseUser(String UserId);

    int selectId(String UserId);

    int selectName(String UserName);

    void insertUser(HashMap<String, String> newUser);

}
