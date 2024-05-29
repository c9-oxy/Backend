package com.example.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Usermapper {

    public int insertUser(HashMap userDTO);
}
