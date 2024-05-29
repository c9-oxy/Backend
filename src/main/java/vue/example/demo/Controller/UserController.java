package vue.example.demo.Controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vue.example.demo.Mapper.Usermapper;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

    @Autowired
    private Usermapper usermapper;

    @GetMapping("/LoginComponent")
    public List<HashMap> getUserList() {
        return usermapper.User_list();
    }

    @PostMapping("/user")
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
                System.err.println(user_yn);
                return user_yn;
            }
        } catch (Exception e) {
            System.err.println("Failed to register user: " + e.getMessage());
            return null;
        }
    }

    @PostMapping("/name")
    public String updateName(@RequestBody HashMap<String, String> newUser) {
        try {
            usermapper.updateName(newUser);
            return "이름이 변경되었습니다.";
        } catch (Exception e) {
            return "Failed to register user: " + e.getMessage();
        }
    }

    @PostMapping("/rank")
    public String updateRank(@RequestBody HashMap<String, String> newUser) {
        try {
            usermapper.updateRank(newUser);
            return "등급이 변경되었습니다.";
        } catch (Exception e) {
            return "Failed to register user: " + e.getMessage();
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String userId) {
        try {
            usermapper.deleteUser(userId);
            return "User deleted successfully";
        } catch (Exception e) {
            return "Failed to delete user: " + e.getMessage();
        }
    }
}
