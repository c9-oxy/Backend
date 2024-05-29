package vue.example.demo.Mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Usermapper { //인터페이스에서 각 쿼리들을 호출이 가능합니다.

    List<HashMap> User_list();

    void insertUser(HashMap<String, String> newUser);

    int checkUser(String UserId);

    HashMap signInUser(HashMap<String, String> newUser);

    String updateUser(HashMap<String, String> newUser);

    void deleteUser(String userId);

    List<HashMap> listUser();

}
