package com.eduglobal.backend.entity;

import com.eduglobal.backend.enums.UserRole;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(exclude = { "level", "progress", "roles" })
@ToString(exclude = { "level", "progress", "roles", "password" })
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String nickname;
	private String password;
	private String email;
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_role", length = 20)
	private UserRole userRole = UserRole.USER;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id")
	private LevelEntity level;

	@Column(name = "level_test_level", length = 10)
	private String levelTestLevel;

	@Column(name = "level_test_score")
	private Integer levelTestScore;

	@Column(name = "level_test_date")
	private java.time.LocalDateTime levelTestDate;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleEntity> roles;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserProgressEntity> progress = new ArrayList<>();
}