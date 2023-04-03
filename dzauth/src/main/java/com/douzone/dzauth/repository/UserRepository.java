package com.douzone.dzauth.repository;

import com.douzone.dzauth.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserRepository {
    Optional<User> findByUsername(String name);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(UserDetails user);
    void update(UserDetails user);
    void delete(String username);
}
