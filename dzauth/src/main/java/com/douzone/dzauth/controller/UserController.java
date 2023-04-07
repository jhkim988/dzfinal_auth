package com.douzone.dzauth.controller;

import com.douzone.dzauth.dto.UserDTO;
import com.douzone.dzauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public void register(@Valid @RequestBody UserDTO userDTO) {
        userService.register(userDTO);
    }

    @PutMapping("/user/{username}")
    public void update(@NotBlank @PathVariable String username, @Valid @RequestBody UserDTO userDTO) {
        userDTO.setUsername(username);
        userService.update(userDTO);
    }

    @DeleteMapping("/user/{username}")
    public void delete(@NotBlank @PathVariable String username) {
        userService.delete(username);
    }

}
