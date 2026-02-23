package com.eduglobal.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VocabularyDto {
    private Long id;
    private String word;
    private String meaning;
    private String exampleSentence;
    private Long levelId;
    private String levelName;
}
