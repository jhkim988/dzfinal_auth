package com.douzone.dzauth.repository;

import com.douzone.dzauth.entity.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Mapper
public interface AuthorityRepository {
    List<Authority> findByUsername(String username);
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int save(@Param("username") String username, @Param("authorities") Collection<? extends GrantedAuthority> authorities);
    void delete(String username);
}
