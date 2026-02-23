package com.eduglobal.backend.dto;

import com.eduglobal.backend.enums.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDto {
    private Long id;
    private String title;
    private String content;
    private Long levelId;
    private String levelName;
    private Category category;
}
