package com.kaka.house.biz.mapper;

import com.kaka.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {


    int insert(User account);
}
