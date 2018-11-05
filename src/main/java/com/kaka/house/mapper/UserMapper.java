package com.kaka.house.mapper;

import com.kaka.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User> selectUsers();
}
