package com.example.repository;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.mapper.Usermapper;

@Repository
public class UserRepository {

    @Autowired
    Usermapper mapper;

    public int insertMember(HashMap userDTO) {
        return mapper.insertUser(userDTO);
    }
}
