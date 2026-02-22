package com.eduglobal.backend.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eduglobal.backend.entity.RoleEntity;
import com.eduglobal.backend.entity.UserEntity;
import com.eduglobal.backend.repository.RoleRepository; // Bu repository-ni yaratmalısan
import com.eduglobal.backend.repository.UserRepository;

@Configuration
public class SetupDataLoader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository; // Rol üçün repository lazımdır

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {

		// 1. Rolları yarat (əgər yoxdursa)
		createRoleIfNotFound("ROLE_GET");
		createRoleIfNotFound("ROLE_ADD");
		createRoleIfNotFound("ROLE_UPDATE");
		createRoleIfNotFound("ROLE_DELETE");

		String myAdminUsername = "tural";
		String myAdminPassword = "abdullayev2004A";

		if (userRepository.findByUsername(myAdminUsername).isEmpty()) {
			UserEntity admin = new UserEntity();
			admin.setUsername(myAdminUsername);
			admin.setPassword(passwordEncoder.encode(myAdminPassword));

			// 2. Bütün rolları tap və Adminə mənimsət
			Set<RoleEntity> allRoles = new HashSet<>(roleRepository.findAll());
			admin.setRoles(allRoles);

			userRepository.save(admin);
			System.out.println("===========================================");
			System.out.println("Admin yaradıldı və bütün rollar təyin edildi!");
			System.out.println("===========================================");
		}
	}

	private void createRoleIfNotFound(String name) {
		if (roleRepository.findByName(name) == null) {
			RoleEntity role = new RoleEntity();
			role.setName(name);
			roleRepository.save(role);
		}
	}
}