package com.eduglobal.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vocabulary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocabularyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String word;

	@Column(nullable = false, length = 500)
	private String meaning;

	@Column(length = 1000)
	private String exampleSentence;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "level_id", nullable = false)
	private LevelEntity level;
}
