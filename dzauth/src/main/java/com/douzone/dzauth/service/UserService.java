package com.douzone.dzauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.douzone.dzauth.dto.UserDTO;
import com.douzone.dzauth.entity.Authority;
import com.douzone.dzauth.entity.SecurityUser;
import com.douzone.dzauth.entity.User;
import com.douzone.dzauth.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserDetailsManager userDetailsManager;
	@Autowired
	private UserRepository userRepository;

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

	public void changePwd(String username, UserDTO.ChangePasswordRequest requestBody) throws IllegalArgumentException {
		// 1.new 와 checkPwd 일치 확인
		// 2.username으로 검색하고 current랑 일치 확인
		// 3.최종으로 update 쿼리 날리기(비밀번호를 암호화 해서 날리기)

		if (!requestBody.getNewPwd().equals(requestBody.getCheckPwd())) {
			throw new IllegalArgumentException("신규 비밀번호가 서로 일치하지 않음");
		}

		UserDetails details = userDetailsManager.loadUserByUsername(username);
		String oldPwd = details.getPassword(); // 암호화 된 친구.
		String currentPwd = requestBody.getCurrentPwd();
		String encodedPwd = passwordEncoder.encode(currentPwd); // 암호화 됌
		System.out.println(oldPwd + ", " + encodedPwd + ", " + currentPwd);
		if (!passwordEncoder.matches(currentPwd, oldPwd)) {
			throw new IllegalArgumentException("기존 비밀번호와 일치하지 않음");
		}

		List<Authority> authority = details.getAuthorities().stream()
				.map(auth -> Authority.builder().username(username).authority(auth.getAuthority()).build())
				.collect(Collectors.toList());
		System.out.println("authority: " + authority);
		userRepository.update(new SecurityUser(User.builder().username(username)
				.password(passwordEncoder.encode(requestBody.getNewPwd())).enabled(true).authority(authority).build()));
	}

	public void delete(String username) {
		userDetailsManager.deleteUser(username);
	}

	private User userDTO2User(UserDTO userDTO) {
		return User.builder().username(userDTO.getUsername()).password(passwordEncoder.encode(userDTO.getPassword()))
				.authority(userDTO.getAuthority().stream()
						.map(authStr -> Authority.builder().username(userDTO.getUsername()).authority(authStr).build())
						.collect(Collectors.toList()))
				.build();
	}
}
