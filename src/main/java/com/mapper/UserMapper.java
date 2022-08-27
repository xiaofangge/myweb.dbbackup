package com.mapper;

import com.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User, Integer> {
    User queryUserByName(String username);

    void updatePwd(@Param("id") Integer id, @Param("newPassword") String newPassword);
}
