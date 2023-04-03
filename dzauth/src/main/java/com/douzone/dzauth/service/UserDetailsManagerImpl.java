package com.douzone.dzauth.service;

import com.douzone.dzauth.entity.SecurityUser;
import com.douzone.dzauth.entity.User;
import com.douzone.dzauth.repository.AuthorityRepository;
import com.douzone.dzauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserDetailsManagerImpl implements UserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Bad Credentials"));
        System.out.println(user);
        return new SecurityUser(user);
    }

    @Override
    public void createUser(UserDetails user) {
        userRepository.save(user);
        authorityRepository.save(user.getUsername(), user.getAuthorities());
    }

    @Override
    public void updateUser(UserDetails user) {
        userRepository.update(user);
        authorityRepository.delete(user.getUsername());
        authorityRepository.save(user.getUsername(), user.getAuthorities());
    }

    @Override
    public void deleteUser(String username) {
        userRepository.delete(username);
        authorityRepository.delete(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Unsupported Operation Exception");
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }
}
