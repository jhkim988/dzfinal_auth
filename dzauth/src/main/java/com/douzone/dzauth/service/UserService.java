package com.douzone.dzauth.service;

import com.douzone.dzauth.dto.UserDTO;
import com.douzone.dzauth.entity.Authority;
import com.douzone.dzauth.entity.SecurityUser;
import com.douzone.dzauth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(UserDTO userDTO) {
        User u = userDTO2User(userDTO);
        userDetailsManager.createUser(new SecurityUser(u));
    }

    public void update(UserDTO userDTO) {
        User u = userDTO2User(userDTO);
        userDetailsManager.updateUser(new SecurityUser(u));
    }

    public void delete(String username) {
        userDetailsManager.deleteUser(username);
    }

    private User userDTO2User(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .authority(userDTO.getAuthority().stream()
                        .map(authStr -> Authority.builder()
                                .username(userDTO.getUsername())
                                .authority(authStr)
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
