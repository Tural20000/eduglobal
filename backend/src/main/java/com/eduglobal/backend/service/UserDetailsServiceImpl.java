package com.eduglobal.backend.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eduglobal.backend.entity.RoleEntity;
import com.eduglobal.backend.entity.UserEntity;
import com.eduglobal.backend.enums.UserRole;
import com.eduglobal.backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles(), user.getUserRole()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<RoleEntity> roles, UserRole userRole) {
		java.util.List<GrantedAuthority> list = (roles == null ? java.util.Collections.emptySet() : roles).stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		if (userRole != null && userRole == UserRole.ADMIN) {
			list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			list.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return list;
	}
}