package com.example.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mapper.Usermapper;

@RestController
@CrossOrigin(origins = "http://localhost:5173")

public class UserController {

    @Autowired
    Usermapper usermapper;

    // UserDTO userDTO
    @PostMapping("/signup")
    public String signup(@RequestBody HashMap<String, String> newUser) {
        System.out.println("받고");
        if (usermapper.insertUser(newUser) > 0) {
            System.out.println("전송됨");
        }
        return "1";
    }

    @GetMapping("/signup")
    public String signups() {
        System.out.println("받고");
        return "1";
    }

    @GetMapping("/aaa")
    public String signupas() {
        System.out.println("받고");
        return "1";
    }

}
/*
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

    @Autowired
    private Usermapper usermapper; // Usermapper 인터페이스 의존성 주입

    @GetMapping("/login")
    public List<HashMap> getUserList() {
        // List<HashMap> userList = usermapper.User_list();
        // for (HashMap user : userList) {
        // System.out.println(user.get("USER_NAME"));
        // }
        return usermapper.User_list(); // 사용자 목록 조회 메서드 호출하여 반환
    }

} 

@Controller
public class MemberController {

    @Autowired
    MemberService ms; ms = usermapper

    @PostMapping("/signup")
    public String signup(MemberDTO memberDTO){
        if(ms.insertMember(memberDTO) > 0){
            return "login";
        }
        return "index";
    }
}
 */
