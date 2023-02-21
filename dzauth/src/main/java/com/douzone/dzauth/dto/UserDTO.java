package com.douzone.dzauth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    @Size(min=1)
    private List<String> authority;
}
