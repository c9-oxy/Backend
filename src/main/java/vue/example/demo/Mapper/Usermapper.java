package vue.example.demo.Mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Usermapper {

    List<HashMap> User_list();

    void insertUser(HashMap<String, String> newUser);

    HashMap signInUser(HashMap<String, String> newUser);

    void updateName(HashMap<String, String> newUser);

    void updateRank(HashMap<String, String> newUser);

    void deleteUser(String userId);

}
