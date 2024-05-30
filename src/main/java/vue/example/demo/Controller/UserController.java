package vue.example.demo.Controller;

import java.awt.PageAttributes;
import java.net.http.HttpHeaders;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vue.example.demo.Mapper.Usermapper;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

import vue.example.demo.error.Message;
import vue.example.demo.error.StatusEnum;

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
    public void updateUser(@RequestBody HashMap<String, String> newUser) {
        try {
            usermapper.updateUser(newUser);
        } catch (Exception e) {
            System.out.println("수정 실패b b cb ");
        }
    }

    @GetMapping("/list")
    public List<HashMap> getUserList() {
        return usermapper.listUser();
    }

    @PostMapping("/User")
    public String registerUser(@RequestBody HashMap<String, String> newUser) {
        try {
            // 새 User 정보를 DB에 삽입
            usermapper.insertUser(newUser);
            return "회원가입 성공";
        } catch (Exception e) {
            System.out.println("회원가입 실패: " + e.getMessage());
            // 예외 발생하면 회원가입 실패 메시지를 반환
            return "회원가입 실패: " + e.getMessage();
        }
    }

    // checkId 메서드는 UserId의 중복을 확인함
    @GetMapping("/check-id/{UserId}")
    public Boolean checkId(@PathVariable String UserId) {
        try {
            int count = usermapper.selectId(UserId);
            // Usermapper에서 메서드를 가져와 count에 저장 int니까 만약 DB에 id값이 있다면 Count가 0에서 1이됨
            if (count > 0) {
                return false;
                // 그럼 conut는 0보다 크니까 if문이 true 상태가 되서 false를 반환
            } else {
                // 만약 조회 했는데 DB에 id값이 없다면 else로 내려와서 true 반환
                return true;
            }
        } catch (Exception e) {
            System.out.println("ID 체크 실패: " + e.getMessage());
            return false;
        }
    }

    // checkName 메서드는 UserName의 중복을 확인함
    @GetMapping("/check-name/{UserName}")
    public Boolean checkName(@PathVariable String UserName) {
        try {
            int count = usermapper.selectName(UserName);
            // Usermapper에서 메서드를 가져와 count에 저장 int니까 만약 DB에 name값이 있다면 Count가 0에서 1이됨
            if (count > 0) {
                return false;
                // 그럼 conut는 0보다 크니까 if문이 true 상태가 되서 false를 반환
            } else {
                // 만약 조회 했는데 DB에 name값이 없다면 else로 내려와서 true 반환
                return true;
            }
        } catch (Exception e) {
            System.out.println("NAME 체크 실패: " + e.getMessage());
            return false;
        }
    }

}
