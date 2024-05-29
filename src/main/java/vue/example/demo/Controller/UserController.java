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

import com.example.dto.UserDTO;

import vue.example.demo.Mapper.Usermapper;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

    UserDTO userDTO = new UserDTO();

    @Autowired

    private Usermapper usermapper;

    @GetMapping("/LoginComponent")
    public List<HashMap> test() {
        return usermapper.User_list();
    }

    @GetMapping("/signin/{UserId}")
    public Boolean checkId(@PathVariable String UserId) {
        try {
            int count = usermapper.checkUser(UserId);
            if (count > 0) {
                System.out.println("id가 존재합니다");
                return true;
            } else {
                System.out.println("id가 존재하지 않습니다.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("ID 체크 실패: " + e.getMessage());
            return false;
        }
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody HashMap<String, String> newUser) {
        try {
            usermapper.insertUser(newUser);
            return "회원가입 성공";
        } catch (Exception e) {
            return "회원가입 실패, " + e.getMessage();
        }
    }

    @PostMapping("/signin")
    public HashMap signInUser(@RequestBody HashMap<String, String> newUser) {
        try {
            HashMap user_yn = usermapper.signInUser(newUser);
            if (user_yn == null) {
                return null;
            } else {
                System.err.println("유저 로그인 " + user_yn);
                return user_yn;
            }
        } catch (Exception e) {
            System.err.println("로그인 에러. Failed to register user: " + e.getMessage());
            return null;
        }
    }

    @PostMapping("/personal-info") //유저 정보 수정 페이지
    public String updateUser(@RequestBody HashMap<String, String> newUser) {
        try {
            usermapper.updateUser(newUser);
            return "수정 성공";
        } catch (Exception e) {
            return "수정 실패, " + e.getMessage();
        }

    }

    @GetMapping("/user-list")
    public List<HashMap> getUserList() {
        return usermapper.listUser();
    }
}
