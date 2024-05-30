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

import vue.example.demo.Mapper.Usermapper;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

    @Autowired

    private Usermapper usermapper;

    @PostMapping("/signin")
    public HashMap signInUser(@RequestBody HashMap<String, String> newUser) {
        try {
            HashMap user = usermapper.getUser(newUser.get("userId"));

            // HashMap user_yn = usermapper.signInUser(newUser);
            if (user == null) {
                return null;
            } else {
                String use_yn = (String) user.get("USER_ACTIVATION");
                if (use_yn.equals("N")) { //유저 상태가 N(비활성) 이라면
                    return null; //null을 반환
                } else {
                    if (newUser.get("userPw").equals(user.get("USER_PW"))) { //비밀번호가 일치할 시(로그인 성공 시)
                        int failCount = Integer.parseInt(String.valueOf(user.get("USER_COUNT"))); //failcount를 데이터베이스에서 호출
                        if (failCount > 0) { //failcount가 존재할 시
                            usermapper.resetFailCount(newUser.get("userId")); //failcount 초기화
                        }
                        user.remove("USER_PW");
                        return user;
                    } else { //비밀번호가 일치하지 않을 시(로그인 실패 시)
                        int failCount = Integer.parseInt(String.valueOf(user.get("USER_COUNT")));
                        if (failCount >= 5) { //실패 카운트가 5이상일 시 비활성화
                            usermapper.notuseUser(newUser.get("userId"));
                            return user;
                        } else {
                            int plus = failCount + 1; //실패 카운트 더하기
                            String plusString = Integer.toString(plus);
                            HashMap<String, String> plusCount = new HashMap<String, String>(); //해시맵에 유저id와 카운트 저장
                            plusCount.put("userId", newUser.get("userId"));
                            plusCount.put("plusCount", plusString);
                            usermapper.plusCounts(plusCount); //함수 호출
                            return user;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("로그인 에러. Failed to register user: " + e.getMessage());
            return null;
        }
    }

    @GetMapping("/user/{userId}")
    public HashMap getMethodName(@PathVariable String userId) {
        try {
            HashMap user = usermapper.getUser(userId);
            return user;
        } catch (Exception e) {
            System.out.println("ID 체크 실패: " + e.getMessage());
            return null;
        }
    }

    @PutMapping("/user")
    public HashMap updateUser(@RequestBody HashMap<String, String> newUser) {
        try {
            usermapper.updateUser(newUser);
            HashMap user = usermapper.getUser(newUser.get("userId"));
            return user;
        } catch (Exception e) {
            System.out.println("수정 실패");
            return null;
        }
    }

    @GetMapping("/list")
    public List<HashMap> getUserList() {
        return usermapper.listUser();
    }
}
