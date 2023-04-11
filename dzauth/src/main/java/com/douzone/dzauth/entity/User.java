package com.douzone.dzauth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private int id;
    private String username;
    private String password;
    private boolean enabled;
    private List<Authority> authority;
}
