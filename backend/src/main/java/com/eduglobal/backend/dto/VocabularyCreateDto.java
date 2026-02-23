package com.eduglobal.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocabularyCreateDto {
    @NotBlank
    private String word;
    @NotBlank
    private String meaning;
    private String exampleSentence;
    @NotNull
    private Long levelId;
}
