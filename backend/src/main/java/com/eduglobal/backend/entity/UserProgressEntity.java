package com.eduglobal.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_progress", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "lesson_id" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProgressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson_id", nullable = false)
	private LessonEntity lesson;

	@Column(nullable = false)
	@Builder.Default
	private Boolean completed = false;

	@Column(nullable = true)
	private Integer score;
}
