package com.eduglobal.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class QuestionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 500)
	private String questions;

	@Column(nullable = false, length = 255)
	private String optionA;

	@Column(nullable = false, length = 255)
	private String optionB;

	@Column(nullable = false, length = 255)
	private String optionC;

	@Column(nullable = false, length = 255)
	private String optionD;

	@Column(nullable = false, length = 1)
	private String correctAnswer; // A, B, C, D

	@Column(nullable = false, length = 2)
	private String level; // A1, A2, B1, B2, C1, C2

}
