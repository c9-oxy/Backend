package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import com.example.dto.UserDTO;
import com.example.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository ur;

    public int insertMember(HashMap userDTO) {
        return ur.insertMember(userDTO);
    }
}
