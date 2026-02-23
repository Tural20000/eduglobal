package com.eduglobal.backend.dto;

import com.eduglobal.backend.enums.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private Long levelId;
    @NotNull
    private Category category;
}
